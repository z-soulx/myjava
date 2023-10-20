package com.example.javabase.java8;

import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2023-09-07 16:38
 **/
public class TestNatigableMap {

	public static void main(String[] args) {
		TreeMap<Integer,Integer> map = new TreeMap<>();
		map.put(123,123);
		map.put(125,125);
		map.put(127,127);
		NavigableMap<Integer, Integer> headMap = map.headMap(125, true);
		Collection<Integer> values = headMap.values();

		Iterator<Integer> iterator = values.iterator();
		boolean b = iterator.hasNext();
		Integer next = iterator.next();
		System.out.println(next);
	}

}
