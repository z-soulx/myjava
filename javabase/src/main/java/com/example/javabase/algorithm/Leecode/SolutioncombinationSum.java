package com.example.javabase.algorithm.Leecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-07-08 14:58
 **/

public class SolutioncombinationSum {
	List<List<Integer>> r = new ArrayList<>();
	public List<List<Integer>> combinationSum2(int[] candidates, int target) {
		Arrays.sort(candidates);
		dfs(candidates,target,0,new ArrayList<Integer>());

		return r;
	}
	@Test
public void test() {
		int[] s = new int[]{10,1,2,7,6,1,5};

	combinationSum2(s,8);

}

	public void  dfs(int[] candidates, int target, int index, List<Integer> l) {
		if (target < 0 || index >= candidates.length) return;
		if (target == 0) {
			r.add(new ArrayList(l));
			return;
		}

		int n = index + 1;
		while (n < candidates.length && candidates[n] == candidates[index]) {
			n++;
		};
		dfs(candidates,target,n + 1,l);
		int s = index;
		while (index <= n) {
			l.add(candidates[index]);
			dfs(candidates,target - (index - s + 1) * candidates[index],n + 1,new ArrayList<Integer> (l));
			index++;
		}


	}
}
