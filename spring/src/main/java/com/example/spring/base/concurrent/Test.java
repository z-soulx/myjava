package com.example.spring.base.concurrent;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2023-09-20 09:38
 **/
public class Test {
	@org.junit.Test
	public void concurrentHashMap(){
		ConcurrentHashMap map = new ConcurrentHashMap();
		Iterator<Object> iterator = map.newKeySet().iterator();
		iterator.hasNext();
	}

	@org.junit.Test
	public void concurrentHashSet(){

	}
}
