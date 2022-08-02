package com.example.javabase.java.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: myjava
 * @description:  验证 syn的可见行   猜测syn只是遵守 H-p的规则，而理想中的可见行 比如
 *
 * 验证 syn哪的操作，对于没有上锁的是否可见？
 * @author: soulx
 * @create: 2022-07-28 13:55
 **/
public class VolatileExample {

	private volatile static boolean flag = false;
	private static int i = 0;
	public static void main(String[] args) throws InterruptedException {
		ReentrantLock lock = new ReentrantLock();
		new Thread(() -> {
			try {
//				lock.lock();
				TimeUnit.MILLISECONDS.sleep(100);
//				synchronized (VolatileExample.class) {

					flag = true;
					System.out.println("flag 被修改成 true");
//				}
//				lock.unlock();

			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		Thread.sleep(1);
		while (!flag) {
			i++;

			Thread.sleep(3000);
//			if (i > 10)
//			lock.lock();
		}
		System.out.println("程序结束,i=" + i);
	}
}

