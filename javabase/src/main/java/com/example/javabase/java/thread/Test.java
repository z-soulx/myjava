package com.example.javabase.java.thread;

import com.example.javabase.java.base.A;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import lombok.SneakyThrows;

/**
 * @program: java-base
 * @description:
 * @author: soulx
 * @create: 2020-02-09 11:28
 **/
public class Test {
    public static void main(String[] args) throws InterruptedException {
    Thread thread = new Thread( new Runnable() {
        @SneakyThrows
        @Override
        public void run() {
            System.out.println(
                "demo"
            );
            Thread.sleep(1000);
            System.out.println("1");

        }
    },"test-zx");
        thread.join();

        thread.setDaemon(true);


        thread.start();
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("k");
                synchronized (thread) {
                    System.out.println("拿到锁");
                    Thread.sleep(6000);
                }
                System.out.println("end");
            }
        }).start();

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("k2");
                synchronized (thread) {
                    System.out.println("拿到锁2");
//                    Thread.sleep(3000);
                }
                System.out.println("end2");
            }
        }).start();
//        Thread.sleep(50);
        System.out.println(
            -1
        );
        Thread.sleep(100000);

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
