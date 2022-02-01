package com.example.spring.aop.proxy.jdk;



import com.example.spring.aop.proxy.jdk.JdkProxy;
import com.example.spring.aop.proxy.jdk.JdkStudent;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * @program: spring
 * @description: jdk代理测试
 * @author: soulx
 * @create: 2020-03-28 09:16
 **/
public class JdkTest {
    /**
     * 1.我们通过实现这个InvocationHandler接口InterfaceProxy来添加自己的切面逻辑，同时把我们的接口实现类HelloImpl作为属性传递到InvocationHandler实例中。
     * 2.因为第一步已经持有了对自己接口Person的实现类JdkStudent的引用，因此可以在invoke方法中调用这个接口的实现类来HelloImpl执行真正的方法调用，这个方法会通过参数传递进来。
     * 3.真正的调用是通过代理类实现的，这个代理类继承了Proxy类，通过带有InvocationHandler参数的构造方法来创建实例，并把InvocationHandler传递到了Proxy类中，它覆写了接口中所有的方法，
     * 同时覆写了Object类的equals、hashCode、toString方法。而这些方法的实现全部都是通过super.h.invoke方法来实现的，我们知道super.h正好就是我们通过构造方法传递给父类的，因此对于接口的调用其实调用的父类的invoke方法来实现的，这里实现类我们已经有了，那么方法如何确定呢？通过源码我们可以看到类的开始就定义了所有需要覆写的方法，这些方法通过static{}代码块来初始化，当我真正调用代理类的say方法的时候其实就是把我们的接口的say方法作为Method类型的参数传递给父类的invoke方法去执行了
     */
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        //生成$Proxy0的class文件
         System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
         //新版JDK
        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
           //包一定要导入jdk的包Proxy
        JdkStudent student = new JdkStudent();
        Person p = (Person) Proxy.newProxyInstance(student.getClass().getClassLoader(),
                student.getClass().getInterfaces(), new JdkProxy(student));
        //调用toString()会走代理
        System.out.println(p);
        System.out.println("$Proxy0.class全名: "+Proxy.getProxyClass(Person.class.getClassLoader(), Person.class));
        p.say();
        System.out.println("=========h================");
        Field h = p.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        Object obj = h.get(p);
        System.out.println(obj.getClass());

    }

}
