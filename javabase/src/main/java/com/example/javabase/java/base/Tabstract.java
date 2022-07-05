package com.example.javabase.java.base;

import org.junit.Test;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-06-29 19:03
 **/
public class Tabstract extends Tabs{


	@Test
	public  void t() {
		Outer t = new Outer();
		Outer.Inner inner = t.new Inner();
		inner.p();
	}



}
