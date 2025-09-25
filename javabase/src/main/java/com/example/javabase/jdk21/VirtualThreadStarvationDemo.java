package com.example.javabase.jdk21;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class VirtualThreadStarvationDemo {
    private static final AtomicInteger completedTasks = new AtomicInteger(0);
    private static final AtomicBoolean shouldStop = new AtomicBoolean(false);
    
    public static void main(String[] args) throws Exception {
        Executors.newVirtualThreadPerTaskExecutor();
        int cpuCores = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU核心数: " + cpuCores);
        System.out.println("ForkJoinPool默认并行度: " + 
            java.util.concurrent.ForkJoinPool.getCommonPoolParallelism());
        
        System.out.println("\n=== 演示1: CPU密集型循环占用所有平台线程 ===");
        demonstrateStarvation(cpuCores);
        
        System.out.println("\n=== 演示2: 对比有阻塞操作的情况 ===");
        demonstrateWithBlocking();
        
        System.out.println("\n=== 演示3: 监控平台线程使用情况 ===");
        monitorPlatformThreads();
    }
    
    private static void demonstrateStarvation(int cpuCores) throws Exception {
        System.out.println("启动 " + cpuCores + " 个CPU密集型虚拟线程...");
        
        CountDownLatch starvingThreadsStarted = new CountDownLatch(cpuCores);
        CountDownLatch victimTaskCompleted = new CountDownLatch(1);
        
        // 启动CPU密集型"饿死"线程
        for (int i = 0; i < cpuCores; i++) {
            final int threadId = i;
            Thread.startVirtualThread(() -> {
                System.out.println("饿死线程-" + threadId + " 开始无限循环");
                starvingThreadsStarted.countDown();
                
                long count = 0;
                // 纯CPU计算，没有任何阻塞调用
                while (!shouldStop.get()) {
                    count++;
                    // 偶尔输出，证明在运行
                    if (count % 2_000_000_000L == 0) {
                        System.out.println("饿死线程-" + threadId + " 计数: " + count);
                    }
                }
                System.out.println("饿死线程-" + threadId + " 停止");
            });
        }
        
        // 等待饿死线程启动
        starvingThreadsStarted.await();
        Thread.sleep(1000);
        
        System.out.println("现在尝试启动一个新的虚拟线程...");
        long startTime = System.currentTimeMillis();
        
        // 尝试启动一个"受害者"任务
        Thread.startVirtualThread(() -> {
            long delay = System.currentTimeMillis() - startTime;
            System.out.println("受害者任务终于开始执行了！延迟: " + delay + "ms");
            victimTaskCompleted.countDown();
        });
        
        // 等待3秒看受害者任务是否能执行
        boolean completed = victimTaskCompleted.await(3, TimeUnit.SECONDS);
        if (!completed) {
            System.out.println("❌ 受害者任务在3秒内未能执行 - 平台线程被占满!");
        } else {
            System.out.println("✅ 受害者任务成功执行");
        }
        
        // 停止饿死线程
        shouldStop.set(true);
        Thread.sleep(500);
        shouldStop.set(false); // 重置
    }
    
    private static void demonstrateWithBlocking() throws Exception {
        System.out.println("启动包含阻塞操作的虚拟线程...");
        
        CountDownLatch latch = new CountDownLatch(10);
        long startTime = System.currentTimeMillis();
        
        // 启动10个包含阻塞操作的虚拟线程
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            Thread.startVirtualThread(() -> {
                try {
                    while (!shouldStop.get()) {
                        // CPU工作
                        for (int j = 0; j < 1000000; j++) {
                            Math.sqrt(j);
                        }
                        
                        // 关键：包含阻塞操作
                        Thread.sleep(10); // 这会让出平台线程
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("任务-" + taskId + " 完成");
                latch.countDown();
            });
        }
        
        Thread.sleep(100);
        
        // 现在启动一个快速任务
        CountDownLatch quickTask = new CountDownLatch(1);
        Thread.startVirtualThread(() -> {
            long delay = System.currentTimeMillis() - startTime;
            System.out.println("✅ 快速任务执行成功！延迟: " + delay + "ms");
            quickTask.countDown();
        });
        
        boolean quickCompleted = quickTask.await(1, TimeUnit.SECONDS);
        System.out.println("包含阻塞操作时，新任务" + (quickCompleted ? "能够" : "无法") + "及时执行");
        
        shouldStop.set(true);
        latch.await(2, TimeUnit.SECONDS);
        shouldStop.set(false);
    }
    
    private static void monitorPlatformThreads() throws Exception {
        System.out.println("监控平台线程使用情况...");
        
        // 启动监控线程
        Thread monitorThread = new Thread(() -> {
            while (!shouldStop.get()) {
                ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
                ThreadGroup parentGroup;
                while ((parentGroup = rootGroup.getParent()) != null) {
                    rootGroup = parentGroup;
                }
                
                Thread[] threads = new Thread[rootGroup.activeCount() * 2];
                int count = rootGroup.enumerate(threads, true);
                
                long platformThreads = 0;
                long virtualThreads = 0;
                
                for (int i = 0; i < count; i++) {
                    if (threads[i] != null) {
                        if (threads[i].isVirtual()) {
                            virtualThreads++;
                        } else {
                            platformThreads++;
                        }
                    }
                }
                
                System.out.println("平台线程: " + platformThreads + ", 虚拟线程: " + virtualThreads);
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        monitorThread.start();
        
        // 创建问题场景
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("创建 " + cores + " 个CPU密集型虚拟线程");
        
        for (int i = 0; i < cores; i++) {
            final int id = i;
            Thread.startVirtualThread(() -> {
                long count = 0;
                while (!shouldStop.get()) {
                    count++;
                    if (count % 1_000_000_000L == 0) {
                        System.out.println("CPU密集型任务-" + id + " 仍在运行");
                    }
                }
            });
        }
        
        Thread.sleep(5000);
        shouldStop.set(true);
        monitorThread.join();
    }
    
    // 演示解决方案
    public static void demonstrateSolution() throws Exception {
        System.out.println("\n=== 解决方案演示 ===");
        
        // 方案1: 添加主动yield
        Thread.startVirtualThread(() -> {
            long count = 0;
            while (!shouldStop.get()) {
                count++;
                
                // 定期主动让出CPU
                if (count % 1000000 == 0) {
                    Thread.yield();
                }
                
                // 或者定期短暂sleep
                if (count % 10000000 == 0) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        });
        
        // 方案2: 使用专门的CPU密集型线程池
        var cpuIntensiveExecutor = java.util.concurrent.Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
        );
        
        cpuIntensiveExecutor.submit(() -> {
            // CPU密集型任务在专门的线程池中运行
            // 不会影响虚拟线程的调度
            while (!shouldStop.get()) {
                // heavy computation
            }
        });
    }
}