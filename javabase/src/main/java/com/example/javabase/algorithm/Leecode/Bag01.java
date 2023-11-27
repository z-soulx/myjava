package com.example.javabase.algorithm.Leecode;

/**
 * @program: myjava
 * @description: 01 背包问题
 * 给你一个可放总重量为W的背包和N个物品，对每个物品，w有重量v和价值
 * 两个属性，那么第i个物品的重量为w[i] ，价值为 v[i]。现在让你用这个背包装物品，问最多能装的价值是多少？
 * @author: soulx
 * @create: 2023-11-22 13:47
 **/
public class Bag01 {
	static int[] weight = {3, 2, 1};
	static int[] weight2 = {0,3, 2, 1};
	static int[] value = {5, 2, 3};
	static int[] value2 = {0, 5, 2, 3};
	static int N = 3;
	static int W = 5;
	public static void main(String[] args) {
		System.out.println(bag01());
		System.out.println(bag01_0());
		System.out.println(bag01_1());
		System.out.println(bag02());

	}

	/**
	 * 先V 后 W， 顺序遍历 且数组不是从0开始
	 */
	public static int bag01() {
		int[][] dp = new int[N + 1][W + 1];
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= W; j++) {
				if (j < weight[i - 1]) {
					dp[i][j] = dp[i - 1][j];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j],dp[i - 1][j - weight[i - 1]] + value[i - 1]);
				}
			}
		}
		return dp[N][W];
	}

	/**
	 * 先W后 V， 顺序遍历 且数组不是从0开始
	 */
	public static int bag02() {
		int[][] dp = new int[N + 1][W + 1];
		for (int j = 1; j <= W; j++) {
		for (int i = 1; i <= N; i++) {
				if (j < weight[i - 1]) {
					dp[i][j] = dp[i - 1][j];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j],dp[i - 1][j - weight[i - 1]] + value[i - 1]);
				}
			}
		}
		return dp[N][W];
	}
	public static int bag01_0() {
		int[][] dp = new int[N + 1][W + 1];
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= W; j++) {
				if (j < weight2[i]) {
					dp[i][j] = dp[i - 1][j];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j],dp[i - 1][j - weight2[i]] + value2[i]);
				}
			}
		}
		return dp[N][W];
	}
	public static int bag01_1() {
		int[][] dp = new int[N + 1][W + 1];
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= W; j++) {
					dp[i][j] = dp[i - 1][j];
				if (j >= weight2[i])
					dp[i][j] = Math.max(dp[i][j],dp[i - 1][j - weight2[i]] + value2[i]);

			}
		}
		return dp[N][W];
	}

}
