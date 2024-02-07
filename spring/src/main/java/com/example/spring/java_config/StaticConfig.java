package com.example.spring.java_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: myjava
 * @description: 静态属性config
 * @author: soulx
 * @create: 2024-02-07 17:08
 **/
@Component
public class StaticConfig {
private static StaticConfigBean bean;

@Autowired
public void setBean(StaticConfigBean bean) {
	StaticConfig.bean = bean;
	System.out.println(bean.getName());
}
}
