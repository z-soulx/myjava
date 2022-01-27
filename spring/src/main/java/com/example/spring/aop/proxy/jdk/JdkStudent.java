package aop.proxy.jdk;

/**
 * @program: spring
 * @description: jdk被代理类，目标类
 * @author: soulx
 * @create: 2020-03-28 09:09
 **/
public class JdkStudent implements Person{
    @Override
    public void say() {
        System.out.println("我是学生...");
    }
}
