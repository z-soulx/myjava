package com.example.spring.aop.proxy.cglib;

/**
 * @program: spring
 * @description:
 * cglib代理基于继承  ：
 *  1 被代理类 + final就会报错
 * @author: soulx
 * @create: 2020-03-29 11:32
 **/
public class HelloImpl {
    //由于是继承实现，不能是final，private 隐含final
    public void say(String name) {
        System.out.println("Hello! " + name);
    }
    public void add() {
        System.out.println("方法内部start");
        say("方法内部调用");
        System.out.println("方法内部end");
    }
    public final void ftest() {
        System.out.println("ftest");
    }
}
