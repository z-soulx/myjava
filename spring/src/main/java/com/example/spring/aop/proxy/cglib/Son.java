package com.example.spring.aop.proxy.cglib;

import org.junit.Test;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-02-01 13:59
 **/
public class Son  extends Father{
    public  int num = 2;

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
		super.holle();
		System.out.println("end2");
	}
}
