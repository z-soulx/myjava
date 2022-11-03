package com.example.javabase.java.base.enums;

/**
 * @program: myjava
 * @description:  证明懒加载
 * @author: soulx
 * @create: 2022-08-24 17:30
 **/
public class Singletone_enum {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("beging sleeping for 5 seconds");
		Thread.sleep(5000);
		System.out.println("wake up!");
		SingletoneENUM ins = SingletoneENUM.INSTANCE;
		System.out.println("done!");
	}

}
