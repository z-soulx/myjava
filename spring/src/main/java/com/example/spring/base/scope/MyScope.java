package com.example.spring.base.scope;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2023-10-20 16:56
 **/

@Component
@Scope("prototype")
public class MyScope {
	@PostConstruct
	public void sayMyScope() {
		System.out.println("sayMyScope");
	}
}
