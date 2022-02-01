package com.example.javabase.java.thread;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @program: myjava
 * @description:
 * 捕获java线程中的逃逸的异常
 * 实现UncaughtExceptionHandler接口，抓住抛出的异常
 * @author: soulx
 * @create: 2022-02-01 16:07
 **/
public class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("caugth: " + e.toString());
	}
}
