package com.example.javabase.java.base;



import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: javabase
 * @description:
 * @author: soulx
 * @create: 2020-01-31 14:51
 **/


public class Test {
  static char c;
    public static void main(String[] args) {
      System.out.println(-0);
      Tabstract2.sys();
//      System.out.println(Tabs.name);
//      System.out.println(Father.name);
//      System.out.println(Son.name);
//      Son.name = "xxxx";
//      System.out.println(Father.name);
//      System.out.println(Son.name);

//     Father a = new Son();
//     a.say();
    }

    @org.junit.Test
  public  void testAbstract() {
//    System.out.println(Tabstract.name2);
//    System.out.println(Tabs.name);
//    System.out.println(Tabstract2.name);

//    System.out.println(Tabstract.name);
//    Tabstract2.name = "xxxx";
//    System.out.println(Tabstract.name);
//    System.out.println(Tabstract2.name);

//      System.out.println(Tabstract2.name);
      Tabstract2.sys();
//      Tabstract2.sys2();
  }

  public <T>void Fx(T t){
        if( t instanceof Test){
            System.out.println("test");
        }
      System.out.println("ss");
  }

@org.junit.Test
/**
 *  编译期常量 初始化问题
 */
  public void t() throws InterruptedException {
  ReentrantLock lo = new ReentrantLock();
  Condition condition = lo.newCondition();
  condition.await();

  System.out.println("end");

//  System.out.println(A.ii);
  System.out.println(A.a);
  System.out.println();


  System.out.println("end");
}

@org.junit.Test
public void anyTest(){
Deque<Integer> stack = new LinkedList<>();
  stack.push(1);
  stack.push(2);
  stack.push(3);
  stack.peek();
  ArrayList<Integer> l = new ArrayList<>(stack);
  LinkedList ls = new LinkedList();
  int[] ints = l.stream().mapToInt(Integer::valueOf).toArray();
  System.out.println(Arrays.toString(ints));

}


public  Integer as2;
  public  static  Integer as;
  /**
   * 测试内部类 static
   */
    class CC {

    A a;

    public  void main() {
      System.out.println(as);
      System.out.println(as2);
    }
}


}


