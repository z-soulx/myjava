package com.example.javabase.java.base;

import org.junit.Test;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-06-29 19:03
 **/
public class Tabstract extends Tabs{
	public  static String name = "bbb";
	public  static String name2 = "cccc";
static {
	System.out.println("Tabstract");
}
	@Test
	public  void t() {
		Outer t = new Outer();
		Outer.Inner inner = t.new Inner();
		inner.p();


	}


	public static void main(String[] args) {
		System.out.println(Tabstract.name);
	}


}
