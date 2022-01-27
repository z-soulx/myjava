package com.example.spring.base.aop.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author skywalker
 */
@Aspect
public class AspectDemo {

    @Pointcut("execution(void com.example.spring.aop.AopDemo.send(..))")
    public void beforeSend() {}

    @Before("beforeSend()")
    public void before() {
        System.out.println("send之前");
    }

}
