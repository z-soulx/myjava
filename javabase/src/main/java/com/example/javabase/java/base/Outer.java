package com.example.javabase.java.base;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-06-29 19:30
 **/
public class Outer {
	private String name = "123";
	class Inner {
		private String name = "222";
		public void  p (){
			System.out.println(Outer.this.name);
		}

		// 定义了一个Inner Class
	}
}
