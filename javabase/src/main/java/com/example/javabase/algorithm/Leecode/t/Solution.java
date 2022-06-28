package com.example.javabase.algorithm.Leecode.t;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-06-27 16:44
 **/
public class Solution {
	List<List<Integer>> r = new ArrayList<>();
	public List<List<Integer>> combinationSum(int[] candidates, int target) {
		combinationSum(candidates,target,new ArrayList<>(),0);
		return r;
	}

	public void combinationSum(int[] candidates, int target, List<Integer> l,int index) {
		if (target == 0) {
			r.add(new ArrayList<>(l));
			return;
		}
		if (target < 0 || index >= candidates.length) return;

		combinationSum(candidates,target,l,index + 1);
		while (target >= candidates[index]) {
			l.add(candidates[index]);
			combinationSum(candidates,target - candidates[index],new ArrayList<>(l),index + 1);
			target = target - candidates[index];

		}


	}
  @Test
	public void test() {
		combinationSum(new int[]{2,3,6,7},7);
	}
}
