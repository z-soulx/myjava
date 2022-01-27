package aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterAdvice;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author skywalker
 */
public class SimpleMethodInterceptor implements MethodBeforeAdvice, AfterReturningAdvice {
//public class SimpleMethodInterceptor implements MethodInterceptor {


//    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("SimpleMethodInterceptor被调用: " + invocation.getMethod().getName());
        return invocation.proceed();
    }


    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("before");
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("after");
    }

    public void before2() throws Throwable {
        System.out.println("before2");
    }
}
