package com.example.javabase.java.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import org.junit.Test;

/**
 * @program: myjava
 * @description: 线程池bug
 * @author: soulx
 * @create: 2022-07-11 19:58
 **/
public class ExecutorTest {


	/**
	 * @Description: https://segmentfault.com/a/1190000042094218
	 * @Param: []  main 方法中测试最好，否者Test容易有系统退出误会
	 * @return: void
	 * @Author: soulx
	 * @Date: 2022-07-11
	 */
	public static void main(String[] args) throws InterruptedException {

		List<Callable<Void>> tasks = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			int finalI = i;
			tasks.add(() -> {
				System.out.println("callable "+ finalI);
				Thread.sleep(500);
				return null;
			});
		}

		ExecutorService executor = Executors.newFixedThreadPool(2);
		Thread executorInvokerThread = new Thread(() -> {
			try {
				executor.invokeAll(tasks);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("invokeAll returned");
		});
		executorInvokerThread.start();


		Thread
				.sleep(800);
		executor.shutdown();
//		System.out.println("shutdown");
//		executor.shutdownNow();
//		System.out.println("shutdown complete");

	}

	@Test
	public void  testFuture() throws ExecutionException, InterruptedException {
		Future f = new FutureTask(()->  "s");
		Object o = f.get();
		System.out.println("123");
	}

}
