package com.example.javabase.java.thread;

import com.example.javabase.java.thread.art_concurrent_book.chapter04.ThreadPool;
import com.zaxxer.hikari.util.UtilityElf.DefaultThreadFactory;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

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
//   t.allowCoreThreadTimeOut();
//   t.execute();

	}
}
