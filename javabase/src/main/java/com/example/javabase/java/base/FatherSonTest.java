package com.example.javabase.java.base;

import org.junit.Test;

/**
 * @program: myjava
 * @description:  父类子类 相关测试，重写，重载 包含静态、字段是否重写等，以及初始化
 * @author: soulx
 * @create: 2022-08-11 20:14
 **/
public class FatherSonTest {

	public static void main(String[] args) {
		Father s = new Son();
		Son s2 = new Son();
		System.out.println(s.id);
		System.out.println(s2.id);
		s.holle();


		//===========
//		System.out.println(Son.name);

	}
	@Test
	public void staticTest(){
//		System.out.println(Son.name); // son没有"重写" name，则Son不会初始化
     Son.staticFf();  // 同上，son没有，则son不会初始化。
//     Father.staticFf();
//		System.out.println(Father.name);
	}

	@Test
	public void final方法Test(){
		Father s = new Son();
		Son s2 = new Son();
    s2.finalFf();
	}


	@Test
	public void 接口中的内部类(){
	A a = new A();
	}

	public static class A extends InFTest.Tee {

	}

//	public static class A2 extends InFTest.Tee2 {
//
//	}


}
