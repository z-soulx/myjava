package com.example.javabase.java.base;

import org.junit.Test;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-02-01 13:59
 **/
public class Son  extends Father{
    public  int num = 2;
    public  String id = "2";
//	public static String name = "son";

  static {
	  System.out.println("static-son");
  }

	@Test
	public void t() {
		System.out.println(super.num);;
//		System.out.println(num);
	}

	@Override
	public void say(){
		System.out.println("before");
		super.say();
		System.out.println("end");
	}

	@Override
	public void holle(){
		System.out.println("before2");
		System.out.println("haha2");
		System.out.println("end2");
	}
	public  void finalFf(){
		System.out.println("son");
	}

//	public static void staticFf(){
//		System.out.println("son");
//	}

	@Test
	public  void mains() {
		System.out.println(id);
//		System.out.println(sid);
//		System.out.println(super.id);
	}
}
