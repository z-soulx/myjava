package com.example.javabase.java;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-07-19 15:39
 **/
public class Cache {

	public static final Map<String, Future<Integer>> SCORE_CACHE = new ConcurrentHashMap<>();

	/**
	 * 查询
	 *
	 * @param userName 用户名
	 * @return {@link Integer}
	 * @throws Exception 异常
	 */
	public Integer query(String userName) throws Exception {
		while (true) {
			Future<Integer> future = SCORE_CACHE.get(userName);
			if (future == null) {
				Callable<Integer> callable = () -> loadFormDB(userName);
				FutureTask futureTask = new FutureTask<>(callable);
				FutureTask<Integer> integerFuture = (FutureTask) SCORE_CACHE.computeIfAbsent(userName, key -> futureTask);
				future = integerFuture;
				integerFuture.run();
//				integerFuture.run
			}
			try {
				return future.get();
			} catch (CancellationException e) {
				SCORE_CACHE.remove(userName, future);
			} catch (Exception e) {
				throw e;
			}
		}
	}

	private Integer loadFormDB(String userName) throws InterruptedException {
		System.out.println("开始查询userName=" + userName + "的分数");
		//模拟耗时
		TimeUnit.SECONDS.sleep(1);
		return ThreadLocalRandom.current().nextInt(380, 420);
	}
}
