package com.example.javabase.java.thread;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program: myjava
 * @description: LinkedBlockingQueue Stream 死循环
 * https://segmentfault.com/a/1190000041975524
 * @author: soulx
 * @create: 2022-07-12 16:02
 **/
public class TestQueue {
	public static void main(String[] args) throws Exception {
		LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>(1000);
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				while (true) {
					queue.offer(new Object());
					queue.remove();
				}
			}).start();
		}
		while (true) {
			System.out.println("begin scan, i still alive");
			queue.stream()
					.filter(o -> o == null)
					.findFirst()
					.isPresent();
			Thread.sleep(100);
			System.out.println("finish scan, i still alive");
		}
	}
}