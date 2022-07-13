package com.example.javabase.java.thread;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Test;

/**
 * @program: myjava
 * @description: 测试并发map
 * https://mp.weixin.qq.com/s/5T3kIyvcoosIIvlWtAGHKw ConcurrentHashMap里面也有死循环
 * @author: soulx
 * @create: 2022-07-13 10:29
 **/
public class TestConcurrentHashMap {

	//
	public static void main(String[] args) {

		Map<String, Integer> map = new ConcurrentHashMap<>(16);
		map.computeIfAbsent(
				"AaAa",
				key -> {
					return map.computeIfAbsent(
							"BBBB",
							key2 -> 42);
				}
		);
		System.out.println("方法结束 map =" + map);
	}

	@Test
	public void test() {
//		Objects.hash()
		//code 一样
		System.out.println("AaAa".hashCode());
		System.out.println("BBBB".hashCode());
	}

}
