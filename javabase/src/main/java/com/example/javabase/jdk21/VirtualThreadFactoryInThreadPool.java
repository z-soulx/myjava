package com.example.javabase.jdk21;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 实验：在传统ThreadPoolExecutor中使用虚拟线程工厂
 * 探索这种混合模式的效果和问题
 */
public class VirtualThreadFactoryInThreadPool {
    
    private static final AtomicInteger taskCounter = new AtomicInteger(0);
    private static final AtomicLong virtualThreadsCreated = new AtomicLong(0);
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== ThreadPoolExecutor + 虚拟线程工厂实验 ===");
        
        // 实验1: 基本行为测试
        basicBehaviorTest();
        
        // 实验2: 队列和线程池参数的影响
//        queueAndPoolParametersTest();
        
        // 实验3: 性能和资源使用对比
        performanceComparison();
        
        // 实验4: 并发压力测试
        concurrencyStressTest();
        
        // 实验5: 异常情况处理
        exceptionHandlingTest();
    }
    
    /**
     * 实验1: 基本行为 - 虚拟线程工厂 + ThreadPoolExecutor
     */
    private static void basicBehaviorTest() throws Exception {
        System.out.println("\n--- 实验1: 基本行为测试 ---");
        
        // 创建虚拟线程工厂
        ThreadFactory virtualThreadFactory = Thread.ofVirtual()
            .name("pooled-virtual-", 0)
            .factory();
        
        // 传统线程池配置 + 虚拟线程工厂
        ThreadPoolExecutor hybridPool = new ThreadPoolExecutor(
            2,                                  // corePoolSize - 仍然有效！
            5,                                  // maximumPoolSize - 仍然有效！
            60L, TimeUnit.SECONDS,              // keepAliveTime - 对虚拟线程意义不大
            new ArrayBlockingQueue<>(3),        // workQueue - 仍然有效！
            virtualThreadFactory,               // 关键：虚拟线程工厂
            new ThreadPoolExecutor.AbortPolicy() // rejectedExecutionHandler - 仍然有效！
        );
        
        System.out.println("混合线程池配置:");
        System.out.println("  核心'虚拟线程'数: " + hybridPool.getCorePoolSize());
        System.out.println("  最大'虚拟线程'数: " + hybridPool.getMaximumPoolSize());
        System.out.println("  队列容量: " + ((ArrayBlockingQueue<?>) hybridPool.getQueue()).remainingCapacity());
        System.out.println("  线程工厂: 虚拟线程工厂");
        
        // 提交任务观察行为
        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            try {
                hybridPool.submit(() -> {
                    String threadInfo = String.format("%s (isVirtual: %s)", 
                        Thread.currentThread().getName(),
                        Thread.currentThread().isVirtual());
                    
                    System.out.printf("任务%d 在线程: %s%n", taskId, threadInfo);
                    
                    try {
                        Thread.sleep(2000); // 长任务
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                
                System.out.printf("  提交任务%d后 - 池大小:%d, 活跃:%d, 队列:%d%n", 
                    i, hybridPool.getPoolSize(), hybridPool.getActiveCount(), 
                    hybridPool.getQueue().size());
                
            } catch (RejectedExecutionException e) {
                System.out.printf("  ❌ 任务%d被拒绝！%n", taskId);
            }
        }
        
        Thread.sleep(1000);
        System.out.println("\n观察结果:");
        System.out.println("  ✅ 虚拟线程仍然受到线程池大小限制");
        System.out.println("  ✅ 队列机制仍然有效");
        System.out.println("  ✅ 拒绝策略仍然有效");
        System.out.println("  ⚠️  但每个'池中的线程'都是虚拟线程");
        
        hybridPool.shutdown();
        hybridPool.awaitTermination(5, TimeUnit.SECONDS);
    }
    
    /**
     * 实验2: 队列和线程池参数对虚拟线程的影响
     */
    private static void queueAndPoolParametersTest() throws Exception {
        System.out.println("\n--- 实验2: 线程池参数对虚拟线程的影响 ---");
        
        ThreadFactory virtualFactory = Thread.ofVirtual().name("test-virtual-", 0).factory();
        
        // 配置1: 小线程池 + 大队列
        ThreadPoolExecutor smallPoolBigQueue = new ThreadPoolExecutor(
            1, 2, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000), // 大队列
            virtualFactory
        );
        
        // 配置2: 大线程池 + 小队列  
        ThreadPoolExecutor bigPoolSmallQueue = new ThreadPoolExecutor(
            10, 20, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2), // 小队列
            virtualFactory
        );
        
        CountDownLatch latch1 = new CountDownLatch(50);
        CountDownLatch latch2 = new CountDownLatch(50);
        
        long start1 = System.currentTimeMillis();
        
        // 测试配置1
        for (int i = 0; i < 50; i++) {
            smallPoolBigQueue.submit(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                latch1.countDown();
            });
        }
        
        latch1.await();
        long duration1 = System.currentTimeMillis() - start1;
        
        long start2 = System.currentTimeMillis();
        
        // 测试配置2
        for (int i = 0; i < 50; i++) {
	        try {
		        bigPoolSmallQueue.submit(() -> {
		            try {
		                Thread.sleep(100);
		            } catch (InterruptedException e) {
		                Thread.currentThread().interrupt();
		            }
		            latch2.countDown();
		        });
	        } catch (Exception e) {
		        e.printStackTrace();
	        }
        }
        
        latch2.await();
        long duration2 = System.currentTimeMillis() - start2;
        
        System.out.printf("小池大队列(虚拟线程): %dms%n", duration1);
        System.out.printf("大池小队列(虚拟线程): %dms%n", duration2);
        
        System.out.println("\n发现:");
        System.out.println("  📝 虚拟线程仍然受线程池大小约束");
        System.out.println("  📝 增加线程池大小仍能提升并发度");
        System.out.println("  📝 但失去了虚拟线程'无限扩展'的优势");
        
        smallPoolBigQueue.shutdown();
        bigPoolSmallQueue.shutdown();
    }
    
    /**
     * 实验3: 性能对比 - 普通线程池 vs 虚拟线程池 vs 混合模式
     */
    private static void performanceComparison() throws Exception {
        System.out.println("\n--- 实验3: 性能对比测试 ---");
        
        int taskCount = 1000;
        int ioDelayMs = 50;
        
        // 1. 传统线程池
        ExecutorService traditionalPool = Executors.newFixedThreadPool(50);
        
        // 2. 纯虚拟线程
        ExecutorService pureVirtualPool = Executors.newVirtualThreadPerTaskExecutor();
        
        // 3. 混合模式：ThreadPoolExecutor + 虚拟线程工厂
        ThreadPoolExecutor hybridPool = new ThreadPoolExecutor(
            50, 50, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            Thread.ofVirtual().factory()
        );
        
        // 性能测试
        long traditionalTime = performanceTest(traditionalPool, taskCount, ioDelayMs, "传统线程池");
        long pureVirtualTime = performanceTest(pureVirtualPool, taskCount, ioDelayMs, "纯虚拟线程");
        long hybridTime = performanceTest(hybridPool, taskCount, ioDelayMs, "混合模式");
        
        System.out.println("\n性能对比结果:");
        System.out.printf("  传统线程池: %dms%n", traditionalTime);
        System.out.printf("  纯虚拟线程: %dms%n", pureVirtualTime);
        System.out.printf("  混合模式:   %dms%n", hybridTime);
        
        System.out.println("\n分析:");
        if (hybridTime > pureVirtualTime) {
            System.out.println("  ⚠️  混合模式比纯虚拟线程慢 - 线程池限制了并发度");
        }
        if (hybridTime < traditionalTime) {
            System.out.println("  ✅ 混合模式比传统线程池快 - 虚拟线程减少了上下文切换开销");
        }
        
        traditionalPool.shutdown();
        pureVirtualPool.shutdown();
        hybridPool.shutdown();
    }
    
    private static long performanceTest(ExecutorService executor, int taskCount, 
                                      int delayMs, String name) throws Exception {
        CountDownLatch latch = new CountDownLatch(taskCount);
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < taskCount; i++) {
            executor.submit(() -> {
                try {
                    Thread.sleep(delayMs); // 模拟IO操作
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                latch.countDown();
            });
        }
        
        latch.await();
        long duration = System.currentTimeMillis() - startTime;
        
        System.out.printf("%s: %d任务耗时 %dms%n", name, taskCount, duration);
        return duration;
    }
    
    /**
     * 实验4: 并发压力测试
     */
    private static void concurrencyStressTest() throws Exception {
        System.out.println("\n--- 实验4: 并发压力测试 ---");
        
        ThreadFactory virtualFactory = Thread.ofVirtual()
            .name("stress-virtual-", 0)
            .factory();
        
        // 测试：有限线程池 + 虚拟线程工厂 在高并发下的表现
        ThreadPoolExecutor stressPool = new ThreadPoolExecutor(
            10, 10, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100), // 有限队列
            virtualFactory,
            new ThreadPoolExecutor.CallerRunsPolicy() // 调用者运行策略
        );
        
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger callerRunCount = new AtomicInteger(0);
        CountDownLatch stressLatch = new CountDownLatch(200);
        
        // 提交200个任务到只有10个线程的池
        for (int i = 0; i < 200; i++) {
            final int taskId = i;
            stressPool.submit(() -> {
                if (Thread.currentThread().isVirtual()) {
                    successCount.incrementAndGet();
                } else {
                    // 在调用者线程中运行
                    callerRunCount.incrementAndGet();
                }
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                stressLatch.countDown();
            });
        }
        
        stressLatch.await();
        
        System.out.println("压力测试结果:");
        System.out.printf("  在虚拟线程中执行: %d个任务%n", successCount.get());
        System.out.printf("  在调用者线程执行: %d个任务%n", callerRunCount.get());
        System.out.printf("  最大池大小被限制在: %d%n", stressPool.getMaximumPoolSize());
        
        System.out.println("\n结论:");
        System.out.println("  📝 即使是虚拟线程，仍受线程池大小限制");
        System.out.println("  📝 溢出的任务仍然会触发拒绝策略");
        System.out.println("  ⚠️  这违背了虚拟线程的设计初衷");
        
        stressPool.shutdown();
    }
    
    /**
     * 实验5: 异常情况处理
     */
    private static void exceptionHandlingTest() throws Exception {
        System.out.println("\n--- 实验5: 异常情况处理 ---");
        
        ThreadFactory virtualFactory = Thread.ofVirtual()
            .name("exception-virtual-", 0)
            .uncaughtExceptionHandler((t, e) -> {
                System.out.printf("虚拟线程 %s 发生未捕获异常: %s%n", t.getName(), e.getMessage());
            })
            .factory();
        
        ThreadPoolExecutor exceptionPool = new ThreadPoolExecutor(
            2, 2, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            virtualFactory
        );
        
        // 提交会抛异常的任务
        Future<?> future1 = exceptionPool.submit(() -> {
            throw new RuntimeException("虚拟线程中的异常");
        });
        
        Future<?> future2 = exceptionPool.submit(() -> {
            System.out.println("正常的虚拟线程任务");
            return "success";
        });
        
        try {
            future1.get();
        } catch (ExecutionException e) {
            System.out.println("✅ 异常正常捕获: " + e.getCause().getMessage());
        }
        
        try {
            Object result = future2.get();
            System.out.println("✅ 正常任务结果: " + result);
        } catch (Exception e) {
            System.out.println("❌ 意外异常: " + e.getMessage());
        }
        
        exceptionPool.shutdown();
    }
    
    /**
     * 总结：混合模式的优缺点分析
     */
    public static void summarizeFindings() {
        System.out.println("\n=== 混合模式总结 ===");
        
        System.out.println("\n✅ 优点:");
        System.out.println("  1. 保留了ThreadPoolExecutor的所有管理特性");
        System.out.println("  2. 减少了平台线程的上下文切换开销");
        System.out.println("  3. 可以精确控制'虚拟线程池'的大小");
        System.out.println("  4. 保留了队列、拒绝策略等传统功能");
        System.out.println("  5. 渐进式迁移的一种方式");
        
        System.out.println("\n❌ 缺点:");
        System.out.println("  1. 失去了虚拟线程无限扩展的核心优势");
        System.out.println("  2. 仍然需要调优线程池参数");
        System.out.println("  3. 复杂性增加但收益有限");
        System.out.println("  4. 违背了虚拟线程的设计理念");
        System.out.println("  5. 可能出现意想不到的行为");
        
        System.out.println("\n🎯 建议:");
        System.out.println("  • 纯虚拟线程: Executors.newVirtualThreadPerTaskExecutor()");
        System.out.println("  • 纯传统线程: ThreadPoolExecutor + 普通工厂");
        System.out.println("  • 避免混合模式，除非有特殊需求");
    }
}