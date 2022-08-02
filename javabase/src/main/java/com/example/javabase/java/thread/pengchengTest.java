package com.example.javabase.java.thread;

import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @program: myjava
 * @description:  多线程顺序打印1-100
 * @author: soulx
 * @create: 2022-08-02 09:53
 **/
public class pengchengTest {

	private static volatile int counter = 0;


	public static void main(String[] args) throws InterruptedException {

		for (int i = 0; i < 5; i++) {
			final int t = i;
			new Thread(() -> {
				while (counter < 100) {
					if (counter % 5 == t) {
						System.out.println(Thread.currentThread().getName() + ":" + counter);
//						counter++;
						int i1 = counter + 1;
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						counter = i1;
					}

					else{
						try {
							TimeUnit.MILLISECONDS.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}, "Thread-" + (i + 1)).start();
		}
		TimeUnit.SECONDS.sleep(10);
	}

	public void t(){
		counter++;
	}

}
