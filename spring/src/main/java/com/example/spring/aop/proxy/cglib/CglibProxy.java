package aop.proxy.cglib;

/**
 * @program: spring
 * @description: cglib代理
 * @author: soulx
 * @create: 2020-03-29 11:22
 **/

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib 动态代理需要2步：
 * 第一步：定义额外的操作
 *  通过实现 MethodInterceptor 接口，来定义在执行代理对象方法前后自己的动作。
 * 第二步：获取代理对象
 *  通过 Enhancer.create 获取代理对象，因为这个只需要。
 */
public class CglibProxy implements MethodInterceptor {
    // 获取代理对象
    public <T> T getProxy(Class<T> clazz) {
        return (T) Enhancer.create(clazz, this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
        after();
        return result;
    }

    private void before() {
        System.out.println("cglib proxy before ...");
    }

    private void after() {
        System.out.println("cglib proxy after ...");
    }

}

