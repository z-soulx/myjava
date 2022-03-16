package com.example.javabase.algorithm.Leecode;

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
import org.junit.Test;

/**
 * @program: java-base
 * @description:
 * @author: soulx
 * @create: 2021-08-10 18:48
 **/
public class T extends Solution {


	public static void main(String[] args) {
//		new HashMap<>().containsKey()
	}



	@Test
	public void test() {
		String bbbab = longestPalindromeSubseq("bbbab");
		System.out.println(bbbab);
	}
	public String longestPalindromeSubseq(String s) {
		boolean[][] dp = new boolean[s.length() ][s.length()];
		int max  = 1;
		int start = 0;
		for (int i = s.length() - 1; i >= 0 ; i--) {
			for (int j = i; j < s.length() ; j++) {
				if (s.charAt(i) == s.charAt(j) ) {
					if (j - i + 1 <= 3) {
						dp[i][j] = true;
					} else {
						dp[i][j] = dp[i + 1][j - 1];
					}
				}
				if (dp[i][j] && j - i + 1 > max) {
					start = i;
					max = j - i + 1;
				}

			}

		}

		return s.substring(start,start + max);
	}





}
