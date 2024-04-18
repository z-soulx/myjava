package com.example.javabase.algorithm.classify.二进制;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2024-04-17 15:24
 **/
public class 二进制加减替代乘除 {

	public static void main(String[] args) {
		System.out.println();
		System.out.println(Mult(10, -3));
		System.out.println(Dvi(-10, -3));
	}

	public static int Mult(int a, int b) {
		int res = 0;
		for (int i = 0; i < 32; i++) {
			res += ((b & (1 << i)) > 0) ? a << i : 0;
		}
		return res;
	}

	//二进制除法运算
	public static int Dvi(int a, int b) {
		int ans = 0;
		boolean f = ((a > 0) ^ (b > 0));
		a = Math.abs(a);
		b = Math.abs(b);
		for (int j = 31; j >= 0; j--) {
			int tmp = a >> j;
			if (tmp >= b) {
				ans = ans | (1 << j);
				a -= (b << j);
			}
		}

		return f ? ~ans + 1 : ans;
	}

}
