package com.example.javabase.algorithm.Leecode;


/**
 * @program: java-base
 * @description:
 * @author: soulx
 * @create: 2021-09-10 15:21
 **/
public class TreeNode {
	public int val;
	public TreeNode left;
	public TreeNode right;
	public TreeNode() {}
	public TreeNode(int val) { this.val = val; }
	public TreeNode(int val, TreeNode left, TreeNode right) {
		this.val = val;
		this.left = left;
		this.right = right;
	}
}
