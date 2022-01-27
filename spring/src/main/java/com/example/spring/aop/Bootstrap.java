package aop;

import base.aop.AopDemo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * AOP测试启动类.
 *
 * @author skywalker
 */
public class Bootstrap {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
//        SimpleAopBean bean = context.getBean(SimpleAopBean.class);
        AopDemo bean1 = context.getBean(AopDemo.class);
        bean1.send();
//        bean.testB();
//        System.out.println(bean.getClass().getSimpleName());
    }

}
