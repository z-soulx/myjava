package com.example.javabase.java.RateLimiter;

import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: myjava
 * @description: Guava限流测试
 * @author: soulx
 * @create: 2022-03-18 13:29
 **/
public class GuavaRateLimiterTest {

	/**
	 *
	 *  后一个线程 阻塞时长 为 上一个获取令牌预支所欠下的时长，若没有欠则不会等待
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
//		new HashMap<>().containsKey()
		RateLimiter rateLimiter = RateLimiter.create(100);
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.submit(()->{
			System.out.println("1-"+rateLimiter.acquire(100));
		});
		Thread.sleep(1);
		executorService.submit(()->{
			System.out.println("2-"+rateLimiter.acquire(350));
		});
		Thread.sleep(1);
		executorService.submit(()->{
			System.out.println("3-"+rateLimiter.acquire(10));
		});
		System.out.println("4-"+rateLimiter.acquire(10));

		System.out.println();
	}
}
