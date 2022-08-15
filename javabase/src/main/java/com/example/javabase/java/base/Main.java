package com.example.javabase.java.base;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-08-15 09:43
 **/
public class Main {

	public static void main(String[] args) throws InterruptedException {
		ConcurrentSkipListSet h = new ConcurrentSkipListSet();
		Iterator iterator = h.iterator();
		Thread.sleep(100);
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

	}

}
