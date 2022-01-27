package aop.proxy.cglib;

/**
 * @program: spring
 * @description:
 * @author: soulx
 * @create: 2020-03-29 11:32
 **/
class HelloImpl {
    //由于是继承实现，不能是final，private 隐含final
    public void say(String name) {
        System.out.println("Hello! " + name);
    }
    public void add() {
        say("方法内部调用");
    }
}
