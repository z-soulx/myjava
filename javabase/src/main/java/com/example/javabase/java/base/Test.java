package com.example.javabase.java.base;



import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
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
     Father a = new Son();
     a.say();
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

  int[] ints = l.stream().mapToInt(Integer::valueOf).toArray();
  System.out.println(Arrays.toString(ints));

}


}


