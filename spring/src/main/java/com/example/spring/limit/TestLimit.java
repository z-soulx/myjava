package com.example.spring.limit;

import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @program: myjava
 * @description: 测试限流+学习
 * @author: soulx
 * @create: 2022-05-13 10:44
 **/
public class TestLimit {

	public static void main(String[] args) throws BlockException {
		SphU.entry("handleResultForAsync");
	}

}
