package aop.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;

import java.lang.reflect.Field;

/**
 * @program: spring
 * @description:
 * @author: soulx
 * @create: 2020-03-29 11:35
 **/
public class TestProxy {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

//通过设置系统属性来输出cglib生成的字节码文件
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\myself\\study_work\\spring-analysis\\cglib");
        CglibProxy cglibProxy = new CglibProxy();
        // cglib 代理的是类，它的实现方式是通过继承一个类作为它的子类来覆盖父类中的方法
        HelloImpl helloProxy = cglibProxy.getProxy(HelloImpl.class);
        //之所以两遍 是因为 代理的嵌套 代理方式一 就会这样
//        helloProxy.add();
//
        helloProxy.say("dada");
        System.out.println("=========CGLIB$CALLBACK_0==========");
        Field h = helloProxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object obj = h.get(helloProxy);
        System.out.println(obj.getClass());

    }
}
