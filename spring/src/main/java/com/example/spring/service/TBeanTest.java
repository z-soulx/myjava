package com.example.spring.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2024-03-26 20:08
 **/
@Configuration
//@Service
public class TBeanTest {
	@Bean
	public TestSe getBean() {
		return new TestSe();
	}
}
