package com.example.javabase.algorithm.Leecode.structure;

/**
 * @program: myjava
 * @description: 链表ListNode
 * @author: soulx
 * @create: 2022-02-14 11:21
 **/
public class ListNode {
	public int val;
	public ListNode next;
	public ListNode() {}
	public ListNode(int val) { this.val = val; }
	public ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
