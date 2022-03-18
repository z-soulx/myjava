package com.example.javabase.algorithm.Leecode;

import com.google.common.util.concurrent.RateLimiter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Test;

/**
 * @program: java-base
 * @description:
 * @author: soulx
 * @create: 2021-08-10 18:48
 **/
public class T extends Solution {






	@Test
	public void test() {
		int[] nums = {-1,1,1,3};

		int tar = 2;
		int l = 0, r = nums.length - 1;
		while (l + 1 < r) {
			int mid = l + (r - l) / 2;
			if (tar > nums[mid]) {
				l = mid;
			} else {
				r = mid;
			}
		}
		System.out.println( l +"___" + r);
	}









}
