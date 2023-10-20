package com.example.spring.base.scope;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2023-10-20 16:55
 **/
@Component
public class MyScopeTest1 {

	@Autowired
	MyScope myScope;

	@PostConstruct
	public void sayMyScope() {
		System.out.println("hello,i am MyScopeTest1, my scope is " + myScope.toString());
	}
}
