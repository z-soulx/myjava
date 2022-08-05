package com.example.javabase.java.jvm;

/**
 * @program: myjava
 * @description: https://segmentfault.com/a/1190000039646449
 * @author: soulx
 * @create: 2022-08-02 14:43
 **/

public class VolatileTest {

	public static volatile int race = 0;

	public static void increase() {
		race++;
	}

	private static final int THREADS_COUNT = 20;

	public static void main(String[] args) {
		Thread[] threads = new Thread[THREADS_COUNT];
		for (int i = 0; i < THREADS_COUNT; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 10000; i++) {
						increase();
					}
				}
			}).start();
		}
		//等待所有累加线程都结束
		while (Thread.activeCount() > 1) {
			System.out.println(Thread.activeCount());
			Thread.yield();
		}
		System.out.println(race);
	}
}


