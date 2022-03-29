package com.example.javabase.algorithm.algorithm4.sort;

import java.util.Arrays;
import org.junit.Test;

/**
 * @program: myjava
 * @description: 快速排序
 * @author: soulx
 * @create: 2022-03-29 10:40
 **/
public class quick {
	@Test
	public void  test(){
     int[] n = {3,2,3,1,2,4,5,5,6};
     kuaipai(n,0,n.length - 1);
//     kuaipai2(n,0,n.length - 1);
		System.out.println(Arrays.toString(n));
	}
  // 普通
	public void  kuaipai (int[] nums,int low, int hight) {
       if (low >= hight) return; //截止
       int j = part(nums,low,hight);
       kuaipai(nums,j + 1,hight);
       kuaipai(nums,low,j - 1);
	}
	// 三分切样
	public void  kuaipai2 (int[] nums,int low, int hight) {
		if (low >= hight) return; //截止
		int l = low; int i = low + 1, j = hight;
		while (i <= j) { //截止
			if (nums[i] < nums[l]) {
				swap(nums, i++, l++);
			} else if (nums[i] == nums[l]) {
				 i++;
			} else {
				swap(nums, i, j--);
			}
		}
		kuaipai2(nums,j + 1,hight);
		kuaipai2(nums,low,l - 1);


	}

	private int part(int[] nums, int low, int hight) {
		int i = low; int j = hight + 1;
		while (true) {
       while (nums[++i] < nums[low]) {
       	if (i == hight) break;
       }
        while (nums[--j] > nums[low]) {
        	if(j == low) {
        		break;
	        }
        }

        if (i >= j) break; //截止
			   swap (nums,i,j);
		}
		swap (nums,low,j);
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
