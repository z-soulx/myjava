package com.example.javabase.test;

import com.lmax.disruptor.dsl.Disruptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-07-19 13:36
 **/
public class T2 {
	 String s ;

	public T2(String s) {
		this.s = s;
	}
	int i= 0;
	public static void main(String[] args) {

		int a = 17;
		int b = -8;
		int c = a/b;
		System.out.println("a:" + Integer.toBinaryString(a));
		System.out.println("b:" + Integer.toBinaryString(b));
		System.out.println("c:" + Integer.toBinaryString(c));
		System.out.println("(a+b-1) >> 3:" + Integer.toBinaryString(b << 3));
		System.out.println("c:" + c);
		System.out.println("(a+b-1) >> 3:" + (a >> 3));

		new BigInteger("1").subtract(new BigInteger("2"));
     new BigDecimal("1.1").subtract(new BigDecimal("2")).toString();

	}
	public void t(){
		i++;
	}

}
