package com.example.javabase.algorithm.Leecode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import org.junit.Test;

/**
 * @program: java-base
 * @description:
 * @author: soulx
 * @create: 2021-08-10 18:48
 **/
public class T extends Solution {


	public static void main(String[] args) {
		int[][] strings = stringToIntArray(
				"[[1,2,3],[4,5,6],[7,8,9]]", 3, 3);
		;

		System.out.println(-3 / 2);
	}

	@Test
	public void test() {
		System.out.println(lengthOfLastWord("Hello World"
		));

	}
	public int lengthOfLastWord(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) <= 'Z' && s.charAt(i) >= 'A')  ||  (s.charAt(i) <= 'z' && s.charAt(i) >= 'a')) {
				count++;
			}
		}
		return count;
	}


}
