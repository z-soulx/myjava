package aop.proxy.cglib;

/**
 * @program: spring
 * @description: Cglib代理的两种实现方式
 * @author: soulx
 * @create: 2020-03-29 17:25
 **/
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.IOException;
import java.lang.reflect.Method;


public class TestMain {
    //继承持有式和继承非持有
    public static void main(String[] args) throws InterruptedException, IOException {


        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TestBean.class);
        enhancer.setCallback(new TBProxy());

        TestBean tb1 = (TestBean) enhancer.create();

//        TestBean tb2 = (TestBean) Enhancer.create(TestBean.class, new TBProxyx(new TestBean()));

        tb1.print();
//        System.out.println("\n\n\n");
//        tb2.printlt();
    }

    // 被代理类
    static class TestBean {


        public void print() {
            System.out.println(this);
            System.out.println("i am printing something");
            t();
        }

        public void t() {
            System.out.println("=== im t() ===");
        }
    }
    // 代理方式1
    static class TBProxy implements MethodInterceptor {

        public TBProxy() {
        }

        public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

            System.out.println("=============  start  ================");

            Object result = methodProxy.invokeSuper(obj, args);
            System.out.println("=============   end   ================");
            return null;
        }
    }
    // 代理方式2
    static class TBProxyx implements MethodInterceptor {

        public TBProxyx(Object obj) {

            this.target = obj;
        }

        Object target;

        public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            System.out.println("=============  startaaa  ================");
            Object result = methodProxy.invoke(target, args);
            System.out.println("=============   endaaa   ================");
            return result;
        }
    }

}