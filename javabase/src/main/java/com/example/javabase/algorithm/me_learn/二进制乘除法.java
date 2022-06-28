package com.example.javabase.algorithm.me_learn;

import com.example.javabase.algorithm.algorithm4.find.BinaryTreeRank;
import java.util.HashMap;
import org.junit.Test;

/**
 * @program: myjava
 * @description: 二进制乘除法
 * @author: soulx
 * @create: 2022-06-14 19:37
 **/
public class 二进制乘除法 {


	public static void main(String[] args) {
		System.out.println(Mult(3,5));
		System.out.println(Mult(-3,5));
		System.out.println(Mult(3,-5));
	}
	@Test
	public void test(){

		System.out.println(Integer.toBinaryString(-5));
	}

	public static int Mult(int a, int b) {
      int sum = 0;
      for (int i = 0; i < 32; i++) {
      	sum += (b & (1 << i)) > 0 ? a << i : 0;
      }

		return sum;
	}

	/**
	 * 推算过程
	 *
	 * https://www.cnblogs.com/zuoxiaolong/p/computer10.html
	 */
	@Test
	public  void  tuisuan(){
//		int a = 17;
//		int b = -8;
		int a = 17;
		int b = -8;
		int c = a/b;
		System.out.println("a:" + Integer.toBinaryString(a));
		System.out.println("b:" + Integer.toBinaryString(b));
		System.out.println("-b:" + Integer.toBinaryString(~b + 1));
		System.out.println("c:" + Integer.toBinaryString(c));
		System.out.println("(a+b-1) >> 3:" + Integer.toBinaryString(((a ) >> 3) ) );
		System.out.println("c:" + c);
		System.out.println("(a+b-1) >> 3:" + (((a) >> 3) ));

	}


}
