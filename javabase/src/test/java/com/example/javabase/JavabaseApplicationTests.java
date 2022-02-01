package com.example.javabase;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class JavabaseApplicationTests {

	@Test
	void contextLoads() {
		productExceptSelf(new int[]{1,2,3,4
});
	}

	public int[] productExceptSelf(int[] nums) {
		int[] l = new int[nums.length];
		int[] r = new int[nums.length];
		l[0] = 1;
		for (int i = 1; i < nums.length; i++) {
			l[i] = l[i] * nums[i - 1];
		}
		r[nums.length - 1] = 1;
		for (int i = nums.length - 2; i >= 0; i--) {
			l[i] = l[i] * nums[i + 1];
		}

		int[] re = new int[nums.length];
		for (int i = 0; i < nums.length; i++) {
			re[i] = l[i] * r[i];
		}
		return re;
	}
}
