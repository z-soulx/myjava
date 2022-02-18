package com.example.javabase.java.thread;

import com.example.javabase.java.base.A;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: java-base
 * @description:
 * @author: soulx
 * @create: 2020-02-09 11:28
 **/
public class Test {
    public static void main(String[] args) {
    ThreadLocal<A> l  = new ThreadLocal<>();
    l.set(new A());

    }

    @org.junit.Test
    /**
     * 线程异常抓捕
     */
    public void ThreadCatchEx(){
        Thread thread = new Thread(() -> {
            System.out.println("111");
            throw new RuntimeException();
        });
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        //这个处理器只有在不存在线程专有的未捕获异常的情况下才会被调用。系统会检查线程的专有的版本，如果没有发现，则检查线程组是有有其专有的uncaughtException()方法。如果也没有，在调用defaultUncaughtExcept
        System.out.println("end");
    }
}
