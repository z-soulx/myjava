package com.example.javabase.java.thread;

import com.example.javabase.java.thread.art_concurrent_book.chapter04.DefaultThreadPool;
import com.example.javabase.java.thread.art_concurrent_book.chapter04.ThreadPool;
import com.zaxxer.hikari.util.UtilityElf.DefaultThreadFactory;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-02-06 16:10
 **/
public class TestThreadPool {

	/**
	 * 当线程池作为局部变量时，方法结束后线程池的线程会被垃圾回收吗？
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
		String jvmName = runtimeBean.getName();
		System.out.println("JVM Name = " + jvmName);
		long pid = Long.valueOf(jvmName.split("@")[0]);
		System.out.println("JVM PID  = " + pid);
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		int n = 20;
		for (int i = 0; i < n; i++) {
//			ThreadPoolExecutor executor = new ThreadPoolExecutor(10,20,1000, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
			ThreadPoolExecutor executor = new ThreadPoolExecutor(0,10,1, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
			for(int j=0;j<10;j++){
				executor.execute(()->{
					System.out.println("当前线程总数为："+bean.getThreadCount());
				});
			}
		}
		Thread.sleep(5000);
		while (true) {
			System.gc();
			System.out.println("线程总数为 = " + bean.getThreadCount());
			Thread.sleep(3000);
		}

	}

	/**
	 * 核心线程超时
	 */
	public void allowCoreThreadTimeOut(){
		ThreadPoolExecutor t = new ThreadPoolExecutor(2,3,0,TimeUnit.SECONDS,new ArrayBlockingQueue(2),
				new DefaultThreadFactory("ss",false),new AbortPolicy());
   t.allowCoreThreadTimeOut(true);
//   t.execute();

	}

	/**
	 * 测试 线程池中超过核心线程的    是持续一定时间后被回收  还是一定时间没有任务生产 才被回收
	 * https://segmentfault.com/a/1190000039815066
	 *
	 */
	@Test
	public void testExecutorService() throws InterruptedException {
		/**
		 *         这个线程最多能容纳的任务是不是 5 个？
		 *         假设任务需要执行 1 秒钟，那么我直接循环里面提交 5 个任务到线程池，肯定是在 1 秒钟之内提交完成，那么当前线程池的活跃线程是不是就是 3 个？
		 *         如果接下来的 30 秒，没有任务提交过来。那么 30 秒之后，当前线程池的活跃线程是不是就是 2 个？
		 */
		ThreadPoolExecutor executorService = new ThreadPoolExecutor(2, 3, 4, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(2),
				Executors.defaultThreadFactory(),
				new ThreadPoolExecutor.DiscardPolicy());

		//每隔两秒打印线程池的信息
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			System.out.println("=====================================thread-pool-info:" + new Date() + "=====================================");
			System.out.println("CorePoolSize:" + executorService.getCorePoolSize());
			System.out.println("PoolSize:" + executorService.getPoolSize());
			System.out.println("ActiveCount:" + executorService.getActiveCount());
			System.out.println("KeepAliveTime:" + executorService.getKeepAliveTime(TimeUnit.SECONDS));
			System.out.println("QueueSize:" + executorService.getQueue().size());
		}, 0, 2, TimeUnit.SECONDS);

		try {
			//同时提交5个任务,模拟达到最大线程数
			for (int i = 0; i < 5; i++) {
				executorService.execute(new Task());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//休眠10秒，打印日志，观察线程池状态
		Thread.sleep(10000);

		//每隔3秒提交一个任务

//		while (true) {
//			Thread.sleep(3000);
//			executorService.submit(new Task());
//		}
		/**
		 * 验证是否 core线程是否固定  结论 没有固定的核心线程
		 */
		int n = 0;
		while (true) {
			if (n < 3 || n > 7) {
				Thread.sleep(3000);
			} else  {
				if (n <5) {
					Thread.sleep(5000);
				} else {
					executorService.submit(new Task());
					executorService.submit(new Task());
					executorService.submit(new Task());
					Thread.sleep(3000);
				}

			}

			executorService.submit(new Task());
			n++;
		}
	}

	static class Task implements Runnable {
		@Override
		public void run(){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread() + "-执行任务");
		}
	}
}
