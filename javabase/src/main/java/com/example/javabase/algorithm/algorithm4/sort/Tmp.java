package com.example.javabase.algorithm.algorithm4.sort;

import org.junit.Test;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-03-29 11:56
 **/
public class Tmp {


	@Test
	public void findKthLargest() {
		int[] nums = {3,2,3,1,2,4,5,5,6};
		int kthLargest = findKthLargest(nums, 4, 0, nums.length - 1);
		System.out.println(kthLargest);
	}
	public int findKthLargest(int[] nums, int k, int low, int hight) {

		if (low >= hight) return -1;
		int part = part(nums,low,hight);
		if (part == k) return k;
		if (part > k) {
			return findKthLargest(nums,k,part + 1, hight);
		}
		return findKthLargest(nums,k,low, part - 1);
	}
	private int part(int[] nums, int low, int hight) {
		int i = low, j = hight + 1;
		while (true) {
			while (nums[++i] < nums[low]) {
				if (i == hight) break;
			}
			while (nums[--j] > nums[low]) {
				if (i == low) break;
			}
			if (i >= j) break;
			swap(nums,i,j);
		}
		swap(nums,low,j);
		return j;
	}
	public void swap (int[] nums,int i, int j){
		if (i != j) {
			nums[i] ^= nums[j];
			nums[j] ^= nums[i];
			nums[i] ^= nums[j];
		}

	}
}
