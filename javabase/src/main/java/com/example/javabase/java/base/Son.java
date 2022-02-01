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

    @Test
	public void t() {
		System.out.println(super.num);;
//		System.out.println(num);
	}
}
