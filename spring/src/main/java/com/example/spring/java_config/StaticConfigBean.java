package com.example.spring.java_config;

import org.springframework.stereotype.Component;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2024-02-07 17:09
 **/
@Component
public class StaticConfigBean implements StaticConfigBeanTest {
private String name = "123";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
