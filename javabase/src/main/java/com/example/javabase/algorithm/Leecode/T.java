package com.example.javabase.algorithm.Leecode;

import com.example.javabase.algorithm.Leecode.structure.ListNode;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
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
		List list = new ArrayList();
//		list.remove()
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(null);
		queue.offer(null);
		queue.offer(null);
		System.out.println(queue.isEmpty());
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

	public static void main(String[] args) {
		List objects = Lists.newArrayList(1,2,3);
		List list = objects.subList(1, 3);
		System.out.println(list);
	}








}
