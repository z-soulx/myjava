package com.example.javabase.algorithm.Leecode;

import org.junit.Test;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-02-22 09:33
 **/
public class leecodeisPerfectSquare {

	@Test
	public  void m() {
		isPerfectSquare(2147483647
		);
	}
	public boolean isPerfectSquare(int num) {
		int left = 0; int right = num;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (mid * mid == num ) return true;
			if (mid * mid > num ) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return false;
	}
}
