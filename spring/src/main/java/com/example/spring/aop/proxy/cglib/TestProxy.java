package com.example.spring.aop.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;

import java.lang.reflect.Field;

/**
 * @program: spring
 * @description:
 * ASM动态
 * cglib代理基于继承  ： 执行的代理方法——实则是：super.fxx()；
 *   1 被代理类 + final就会报错
 *   2 被代理类的方法是final 则调用后 不会有 before after 之前之后的执行
 *   3 含有内部方法调用 代理类调用该方法  before after 会调用两次， (由于每个非final的方法都被代理了)
 *      add() 会触发2次before after 镶嵌顺序
 *
 *   jdk动态代理的拦截对象是通过反射的机制来调用被拦截实例方法的，反射的效率比较低，
 *   所以cglib采用了FastClass的机制来实现对被拦截方法的调用。FastClass机制就是对一个类的方法建立索引，
 *   调用方法时根据方法的签名来计算索引，通过索引来直接调用相应的方法。
 * 这也就是为什么CGLIB生成的动态代理会包含3个class文件的原因，其中一个是生成的代理类，
 * 另外两个类都是FastClass机制需要的。另外两个类都继承了FastClass这个类。
 * 其中一个class为生成的代理类中的每个方法建立了索引，另外一个则为我们被代理类的所有方法包含其父类的方法建立了索引。
 *
 * @author: soulx
 * @create: 2020-03-29 11:35
 **/
public class TestProxy {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

//通过设置系统属性来输出cglib生成的字节码文件
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\myself\\study_work\\spring-analysis\\cglib");
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/soulx/Desktop/PG/my_learn/myjava/spring/src/main/java/com/example/spring/aop/poxytest/cglib_proxy");
        CglibProxy cglibProxy = new CglibProxy();
        // cglib 代理的是类，它的实现方式是通过继承一个类作为它的子类来覆盖父类中的方法
        HelloImpl helloProxy = cglibProxy.getProxy(HelloImpl.class);
        //之所以两遍 是因为 代理的嵌套 代理方式一 就会这样
        helloProxy.add();

//        helloProxy.ftest();
//
        helloProxy.say("dada");
        System.out.println("=========CGLIB$CALLBACK_0==========");
        Field h = helloProxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object obj = h.get(helloProxy);
        System.out.println(obj.getClass());

    }
}
