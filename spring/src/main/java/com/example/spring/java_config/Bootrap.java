package com.example.spring.java_config;

import com.example.spring.base.SimpleBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * @author skywalker
 */
public class Bootrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SimpleBeanConfig.class);
        SimpleBean simpleBean = context.getBean(SimpleBean.class);
        SimpleBean simpleBean2 = context.getBean(SimpleBean.class);
        System.out.println(simpleBean.toString());
        System.out.println(simpleBean2.toString());
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
    }

}
