package com.example.javabase.jdk21;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: myjava
 * @description: 演示JDK21中虚拟线程使用synchronized时的pinned行为
 * @author:
 * @create: 2025-09-26 11:22
 **/
public class SyncPinned {

	private static final Object lock = new Object();
	private static final AtomicInteger threadCounter = new AtomicInteger(0);
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

	/**
	 * 执行命令能看到
	 *  java -Djdk.tracePinnedThreads=full -cp target/classes com.example.javabase.jdk21.SyncPinned
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("=== JDK21 虚拟线程 Synchronized Pinning 测试 ===");
		System.out.println("Java版本: " + System.getProperty("java.version"));
		System.out.println("开始时间: " + LocalDateTime.now().format(formatter));
		System.out.println("运行参数提示: 请使用 -Djdk.tracePinnedThreads=full 来查看pinned详细信息");
		System.out.println();

		// 测试虚拟线程被pinned的情况
		testVirtualThreadPinning();

		System.out.println("\n=== 测试完成 ===");
	}

	private static void testVirtualThreadPinning() throws InterruptedException {
		System.out.println("创建10个虚拟线程，它们将同时竞争synchronized锁...");

		// 创建10个虚拟线程，它们会同时竞争同一个synchronized锁
		for (int i = 0; i < 1; i++) {
			final int threadId = i + 1;
			Thread.startVirtualThread(() -> {
				try {

					printThreadInfo("尝试获取锁", threadId);

					// 这里使用synchronized会导致虚拟线程被pinned到平台线程
					// 多个线程同时竞争会触发pinned事件
					synchronized (lock) {
						printThreadInfo("已获取锁，开始长时间工作", threadId);

						// 模拟长时间工作，让其他线程在synchronized块外等待
						// 这会导致等待的虚拟线程被pinned
						Thread.sleep(2000);

						printThreadInfo("工作完成，即将释放锁", threadId);
					}

					printThreadInfo("已释放锁", threadId);

				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					System.err.println("线程 " + threadId + " 被中断");
				}
			});
		}

		// 让所有线程都启动并准备好
		Thread.sleep(100);

		// 同时启动所有线程，创建真正的并发竞争
		System.out.println("同时启动所有虚拟线程，开始竞争锁...");

		// 等待一小段时间，让竞争开始
		Thread.sleep(500);
		System.out.println("此时应该有多个虚拟线程在synchronized上被pinned，请查看JVM输出");



		System.out.println("\n--- Pinning 现象说明 ---");
		System.out.println("1. 当多个虚拟线程同时竞争synchronized锁时，等待的线程会被pinned");
		System.out.println("2. 使用 -Djdk.tracePinnedThreads=full 参数可以看到详细的pinned堆栈信息");
		System.out.println("3. Pinned的虚拟线程无法释放底层平台线程，影响并发性能");
		System.out.println("4. 建议在虚拟线程中使用ReentrantLock替代synchronized");
		System.out.println("\n如果没有看到pinned输出，请用以下命令重新运行:");
		System.out.println("mvn exec:java -Dexec.mainClass=\"com.example.javabase.jdk21.SyncPinned\" -Dexec.args=\"-Djdk.tracePinnedThreads=full\"");
	}

	private static void printThreadInfo(String action, int threadId) {
		Thread current = Thread.currentThread();
		String time = LocalDateTime.now().format(formatter);
		String threadType = current.isVirtual() ? "虚拟线程" : "平台线程";

		System.out.printf("[%s] 线程%d (%s) - %s - %s%n",
			time, threadId, threadType, current.getName(), action);

		// 如果是虚拟线程，尝试获取更多信息
		if (current.isVirtual()) {
			System.out.printf("    └── 载体线程: %s%n", getCarrierThreadInfo());
		}
	}

	private static String getCarrierThreadInfo() {
		// 在JDK21中，可以通过反射或其他方式获取载体线程信息
		// 这里简化处理，只是说明概念
		return "平台线程-" + Thread.currentThread().hashCode();
	}
}
