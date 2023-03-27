package com.example.javabase.java.base;

/**
 * @program: myjava
 * @description:   接口属性测试
 * @author: soulx
 * @create: 2022-02-01 15:05
 **/
public interface InFTest {
	public static final int a = 2;
	public abstract String tt();

	// 可以被继承
	public static   class Tee{

	}
	// 不可以被继承
	public static  final class Tee2{

	}


}
