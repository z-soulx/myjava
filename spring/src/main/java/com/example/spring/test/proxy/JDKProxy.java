package com.example.spring.test.proxy;

import java.lang.reflect.Proxy;

/**
 * 测试JDK动态代理.
 *
 * @author skywalker
 */
public class JDKProxy {

    public static void main(String[] args) {
        com.example.spring.test.proxy.UserService userService = new com.example.spring.test.proxy.UserServiceImpl();
        com.example.spring.test.proxy.UserService proxy = (com.example.spring.test.proxy.UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(),
                new Class[]{com.example.spring.test.proxy.UserService.class}, new Handler(userService));
        proxy.printName();
    }

}
