package com.example.javabase.jdk21;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.time.Instant;
import java.time.Duration;

/***
 * 对比传统平台线程池与虚拟线程工厂池在 CPU 密集任务下的表现差异
 * 证明 虚拟线程还是受限于 CPU 核心数，不能无限并行，特别是cpu密集型任务
 */
public class ThreadPoolComparison {
    private static final AtomicLong totalSum = new AtomicLong(0); // 全局累加，防 JIT 优化

    public static void main(String[] args) {
        int cpuCores = Runtime.getRuntime().availableProcessors();
        System.out.println("Available CPU cores: " + cpuCores);

        // 传统平台线程池
        testPool("Platform Thread Pool", Executors.defaultThreadFactory(), cpuCores);

        // 虚拟线程池
        ThreadFactory virtualFactory = Thread.ofVirtual().factory();
        testPool("Virtual Thread Pool", virtualFactory, cpuCores);
    }

    private static void testPool(String name, ThreadFactory factory, int cpuCores) {
        System.out.println("\n=== Testing " + name + " ===");

        ThreadPoolExecutor pool = new ThreadPoolExecutor(
            30, 30, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(90), factory, new ThreadPoolExecutor.AbortPolicy());

        AtomicInteger concurrentCount = new AtomicInteger(0);
        AtomicInteger maxConcurrent = new AtomicInteger(0);
        CountDownLatch endLatch = new CountDownLatch(40); // 只用于等待所有（包括拒绝后手动处理）

        Instant startTime = Instant.now();

        int submitted = 0;
        for (int i = 0; i < 40; i++) {
            final int taskId = i;
            final long loopCount = (taskId < 30) ? 10_000_000_000L : 500_000_000L; // 长~10s, 短~0.5s，调整以匹配你的机器

            try {
                pool.submit(() -> {
                    int current = concurrentCount.incrementAndGet();
                    maxConcurrent.set(Math.max(maxConcurrent.get(), current));

                    System.out.println(Instant.now() + " Task " + taskId + " started on " + Thread.currentThread().getName() +
                        " (virtual: " + Thread.currentThread().isVirtual() + "), concurrent: " + current);

                    // CPU 密集：防优化，用全局 totalSum
                    long sum = 0;
                    for (long j = 0; j < loopCount; j++) {
                        sum += (j % 2 == 0) ? 1 : -1;
                    }
                    totalSum.addAndGet(sum); // 使用 sum，防止 JIT 消除循环

                    System.out.println(Instant.now() + " Task " + taskId + " done");
                    concurrentCount.decrementAndGet();
                    endLatch.countDown();
                });
                submitted++;
            } catch (RejectedExecutionException e) {
                System.out.println("Task " + taskId + " rejected: " + e.getMessage());
                endLatch.countDown(); // 拒绝任务也 countDown，避免 hang
            }
        }

        try {
            endLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Instant endTime = Instant.now();
        Duration totalTime = Duration.between(startTime, endTime);

        System.out.println("Submitted tasks: " + submitted);
        System.out.println("Max concurrent tasks: " + maxConcurrent.get());
        System.out.println("Total execution time: " + totalTime.getSeconds() + " seconds");
        System.out.println("Estimated effective parallelism: ~" + String.format("%.1f", (30 * 10.0 / totalTime.getSeconds())));
        System.out.println("Total sum (for anti-optimization): " + totalSum.get());

        pool.shutdown();
    }
}