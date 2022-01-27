package com.example.javabase.algorithm.Leecode;

import com.alibaba.fastjson.JSONArray;
import java.util.Arrays;
import java.util.Collection;

/**
 * @program: java-base
 * @description: leetcode 自测框架
 * @author: soulx
 * @create: 2021-07-30 16:26
 **/
public abstract class Solution {

	public void print(Collection list) {
//		for (T t : list) {
			System.out.println(list);
//		}
	}


	public void print(int[] list) {
		System.out.println(Arrays.toString(list));
	}

	/**
	 * String -> [][]二维数组
	 * @param input
	 * @return
	 */
	public static String[][] stringToStringArray(String input) {
		String[][] r = new String[9][9];
		JSONArray jsonArray = JSONArray.parseArray(input);
		JSONArray[] arr = new JSONArray[jsonArray.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = jsonArray.getJSONArray(i);
			for (int ii = 0; ii < arr[i].size(); ii++) {
				r[i][ii] = arr[i].getString(ii);
			}
		}
		return r;
	}
	public static char[][] stringToCharArray(String input) {
		char[][] r = new char[9][9];
		JSONArray jsonArray = JSONArray.parseArray(input);
		JSONArray[] arr = new JSONArray[jsonArray.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = jsonArray.getJSONArray(i);
			for (int ii = 0; ii < arr[i].size(); ii++) {
				r[i][ii] = arr[i].getString(ii).charAt(0);
			}
		}
		return r;
	}
	public static int[][] stringToIntArray(String input, int l, int l2) {
		int[][] r = new int[l][l2];
		JSONArray jsonArray = JSONArray.parseArray(input);
		JSONArray[] arr = new JSONArray[jsonArray.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = jsonArray.getJSONArray(i);
			for (int ii = 0; ii < arr[i].size(); ii++) {
				r[i][ii] = arr[i].getInteger(ii);
			}
		}
		return r;
	}
}
