package com.example.javabase.algorithm.Leecode;

import java.util.EnumSet;
import org.junit.Test;

/**
 * @program: java-base
 * @description:
 * @author: soulx
 * @create: 2021-08-10 18:48
 **/
public class T extends Solution {


	public static void main(String[] args) {


	}

	@Test
	public void test() {
		int[] a = {0, 0, 0, 0,0, 1};
		int[] b = {1, 0, 0, 0,0, 0};

		System.out.println(s(a,b));

	}

	public int s(int[] a, int[] b) {
		int[][] dp = new int[a.length][b.length];
    for (int k = 0; k < a.length; k++) {
    	if (a[0] == b[k])  {
    		dp[0][k] = 1;
	    }
    	if (a[k] == b[0]) {
    		dp[k][0] = 1;
	    }
		}
   int max = 0;
    for (int i = 1; i < a.length; i++) {
    	for (int j = 1; j < b.length; j++) {
    		 if (a[i] == b[j]) {
			     dp[i][j] = dp[i - 1][j - 1] + 1;
		     } else  {
    		 	dp[i][j] = 0;
		     }
		    max = Math.max(max,dp[i][j]);
	    }
    }
    return max;
	}




}
