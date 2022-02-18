package com.example.javabase.algorithm.Leecode;

import java.util.Arrays;
import org.junit.Test;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2022-02-10 17:23
 **/
public class KMP {
  @Test
	public void m() {
	  System.out.println(strStr("aabaaabaaac",
			  "aabaaac"

	  ));;
	}

	public int strStr(String haystack, String needle) {
		int[] index = new int[needle.length()];
		int k = -1;
		index[0] = -1;
		for (int i = 0; i < needle.length() - 1; i++) {
       if (k == -1 || needle.charAt(i) == needle.charAt(k)) {
       	index[i + 1] = ++k;
       } else {
       	k = index[k];
       	i--;
       }
		}
		System.out.println(Arrays.toString(index));
		int j = 0;
		for (int i = 0; i < haystack.length(); i++) {
			if (j == -1 || haystack.charAt(i) == needle.charAt(j)) {
				j++;
			} else {
				j = index[j];
				i--;
			}
			if (j == needle.length()) return i - j + 1;
		}




		return  -1;
	}
}
