/**
 * 
 */
package com.example.javabase.java.thread.art_concurrent_book.chapter05;

import java.util.concurrent.locks.Lock;

import com.example.javabase.java.thread.art_concurrent_book.chapter04.SleepUtils;
import org.junit.Test;

/**
 * 10-11
 */
public class TwinsLockTest {
@Test
    public void test() {
        final Lock lock = new TwinsLock();
        class Worker extends Thread {
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepUtils.second(1);
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        // ����10���߳�
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        // ÿ��1�뻻��
        for (int i = 0; i < 10; i++) {
            SleepUtils.second(1);
            System.out.println();
        }
    }
}
