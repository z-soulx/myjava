package com.example.javabase.java.base;

import org.junit.Test;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-02-18 13:59
 **/
public class StringTest {
 public  String  a ="1s1s";
	@Test
	public void inter(){
		String s = new String("1s");
		s.intern();
		String s2 = "1s";
		System.out.println(s == s2);
		String s3 = new String("1s") + new String("1s");
		s3.intern();
		String s4 = "1s1s";
		System.out.println(s3 == s4);
	}

	public static void main(String[] args) {
		new StringTest().inter();
//		String s = new String("1s");
//		s.intern();
//		String s2 = "1s";
//		System.out.println(s == s2);
//		String s3 = new String("1s") + new String("1s");
//		s3.intern();
//		String s4 = "1s1s";
//		System.out.println(s3 == s4);
//		System.out.println(intern == s4);
	}
}
