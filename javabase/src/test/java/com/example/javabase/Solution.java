package com.example.javabase;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-11-07 16:50
 **/
import java.util.*;


public class Solution {
	/**
	 *
	 * @param n int整型 the n
	 * @return int整型
	 */
	int count = 0;
	public int Nqueen (int n) {
		Set<Integer> clnum = new HashSet<>(), fk = new HashSet<>();
		Set<Double> k = new HashSet<>();
		// write code here
		Nqueen2(n, 1,clnum,k,fk);
		return count;

	}

	public void Nqueen2 (int n, int c, Set<Integer>  clnum,
			Set<Double>  k, Set<Integer> fk) {

		for (int i = 1; i <= n; i++) {
			if (clnum.contains(i) || k.contains((double) i- c) || fk.contains(c+i)) {
				continue;
			}
			if (c == n) {
				count++;
       break;
			}


			clnum.add(i);
			k.add((double) i-c);
//			k.add((double) i/c);
			fk.add(c+i);
			Nqueen2(n,c+ 1,clnum,k,fk);
			clnum.remove(i);
			k.remove((double) i-c);
//			k.remove((double) i/c);
			fk.remove(c+i);
		}



	}

	public static void main(String[] args) {
		System.out.println(new Solution().Nqueen(4));
	}
}
