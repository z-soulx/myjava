package aop.proxy.jdk;



import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @program: spring
 * @description: jdk代理类 实现
 * @author: soulx
 * @create: 2020-03-28 09:10
 **/
public class JdkProxy implements InvocationHandler {

    private Object target;

    public JdkProxy() {
        super();
    }

    public JdkProxy(Object target) {
        this.target = target;
    }
    /**
     该方法负责集中处理动态代理类上的所有方法调用。
     第一个参数既是代理类实例，
     第二个参数是被调用的方法对象
     第三个方法是调用参数。
     调用处理器根据这三个参数进行预处理或分派到委托类实例上发射执行
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        System.out.println("------插入前置通知代码-------------");
        Object obj = method.invoke(target, args);
        System.out.println("------插入后置通知代码-------------");
        return obj;
    }
}

