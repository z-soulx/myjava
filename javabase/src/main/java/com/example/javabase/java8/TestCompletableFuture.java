package com.example.javabase.java8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-05-18 14:32
 **/
public class TestCompletableFuture {

	public static void main(String[] args) throws Exception {
		零依赖CompletableFuture的创建();
	}

	private static void 零依赖CompletableFuture的创建()
			throws InterruptedException, ExecutionException {
				ExecutorService executor = Executors.newCachedThreadPool();

//方式1、使用runAsync或supplyAsync发起异步调用
		CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
			return "result1";
		}, executor);
		System.out.println(cf1.get());
//方式2、CompletableFuture.completedFuture()直接创建一个已完成状态的CompletableFuture
		CompletableFuture<String> cf2 = CompletableFuture.completedFuture("result2");
//方式3、先初始化一个未完成的CompletableFuture，然后通过complete()、completeExceptionally()，完成该CompletableFuture
		CompletableFuture<String> cf = new CompletableFuture<>();
		cf.complete("success");
		System.out.println(cf.get());
	}
}
