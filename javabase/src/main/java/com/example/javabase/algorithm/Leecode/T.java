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
		compareVersion("0.1",
				"1.1"
		);
	}

	public int compareVersion(String version1, String version2) {

		String[] v1 = version1.split("\\.");
		String[] v2 = version2.split("\\.");
		for (int i = 0; i < Math.min(v1.length,v2.length);i++) {
			if (cp(v1[i]) > cp(v2[i])) {
				return 1;
			}
			if (cp(v1[i]) < cp(v2[i])) {
				return -1;
			}
		}
		return 0;
	}
	public int cp(String s){
		return Integer.valueOf(s);
	}









}
