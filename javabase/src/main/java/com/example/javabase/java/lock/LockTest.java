package com.example.javabase.java.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.junit.Test;

/**
 * @program: myjava
 * @description: 锁
 * @author: soulx
 * @create: 2022-01-27 18:31
 **/
public class LockTest {



	@Test
	/**
	 *  可重入锁
	 */
	public void ReentrantLock(){
		ReentrantLock re = new ReentrantLock();
		new Thread(new Runnable() {
			@Override
			public void run() {
				re.lock();
				try {
					System.out.println("11");
					Thread.sleep(4000);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				re.unlock();
				System.out.println("222");
			}
		}).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("33");

		re.lock();
		re.unlock();
		Condition condition = re.newCondition();
		try {
			condition.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
	}

	@Test
	/**
	 * CountDownLatch
	 */
	public void CountDownLatch(){
		CountDownLatch l = new CountDownLatch(2);
		try {

			new Thread(()->{
				try {
					System.out.println("任务2");
					Thread.sleep(2000);
					l.await();
					System.out.println("任务2等待结束");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();

			new Thread(()->{
				try {
					l.await();
					System.out.println("TT");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();

			System.out.println("TT1");

			new Thread(()->{
				try {
					Thread.sleep(2000);
					l.countDown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();

			l.countDown();
			System.out.println("-1");
			l.await();
//			Thread.sleep(1000);
			System.out.println("end");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * CyclicBarrier
	 */
	public void CyclicBarrier(){
		CyclicBarrier l = new CyclicBarrier(2);
		try {
			l.await();
			l.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	/**
	 * Semaphore
	 * ws==0和!compareAndSetWaitStatus(h, 0, Node.PROPAGATE) 是两个矛盾的场景，
	 */
	public void Semaphore(){
		Semaphore l = new Semaphore(2);
		try {
			// 多次次release方法
//			l.release(100);
//			l.acquire(3);

			// 一个线程重复调用11次acquire方法
			l.acquire();
			l.acquire();
			l.acquire();
			System.out.println("获取");

			l.acquire();
			System.out.println("获取2");
			l.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * ReadWriteLock
	 */
	public void ReadWriteLock(){
		ReadWriteLock l = new ReentrantReadWriteLock();
		Lock read = l.readLock();
		Lock write = l.writeLock();
		try {
			read.lock();
			read.unlock();
			write.lock();
			write.unlock();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
