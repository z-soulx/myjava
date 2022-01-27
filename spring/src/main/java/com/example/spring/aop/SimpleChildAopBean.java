package com.example.spring.aop;

import com.example.spring.aop.SimpleAopBean;

/**
 * @author skywalker
 */
public class SimpleChildAopBean extends SimpleAopBean {

    @Override
    public void testC() {
        System.out.println("child testC");
    }
}
