package com.example.spring.test.dyload;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class TestHotDeploy {
    static Object instance;
    public static void main(String[] args) throws Exception {

        CustomClassLoader loader = new CustomClassLoader("D:\\");
        Class<?> cls = loader.loadClass("HelloWorld");
        instance = cls.getDeclaredConstructor().newInstance();
            Method sayHelloMethod = cls.getDeclaredMethod("sayHello");
            sayHelloMethod.invoke(instance);
        Class<?> cls2 = loader.loadClass("HelloWorld");
        System.out.println(cls == cls2);
        sayHelloMethod.invoke(instance);


    }
}
