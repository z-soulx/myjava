package com.example.javabase.java.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
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
	 * ReadWriteLock
	 */
	public void ReadWriteLock() {
		ReadWriteLock l = new ReentrantReadWriteLock();
		Lock read = l.readLock();
//		Condition condition = read.newCondition(); // 不支持
		Lock write = l.writeLock();
		Condition condition1 = write.newCondition();
		try {
			read.lock();
			read.unlock();
			write.lock();
			write.unlock();

		} catch (Exception e) {
			e.printStackTrace();
		}
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
	 * StampedLock 1.8的，版本控制概念的锁，防止写饥饿
	 */
	public void StampedLock() {
		StampedLock stampedLock = new StampedLock();
		int number = 0;

		long stamp = stampedLock.tryOptimisticRead();
		int result = number;
		//故意间隔4秒钟，很乐观认为读取中没有其它线程修改过number值，具体靠判断
		System.out.println("4秒前stampedLock.validate方法值(true无修改，false有修改)"+"\t"+stampedLock.validate(stamp));
		for (int i = 0; i < 4; i++) {
			try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
			System.out.println(Thread.currentThread().getName()+"\t"+"正在读取... "+i+" 秒" +
					"后stampedLock.validate方法值(true无修改，false有修改)"+"\t"+stampedLock.validate(stamp));
		}
		if(!stampedLock.validate(stamp))
		{
			System.out.println("有人修改过------有写操作");
			stamp = stampedLock.readLock();//从乐观读 升级为 悲观读
			try
			{
				System.out.println("从乐观读 升级为 悲观读");
				result = number;
				System.out.println("重新悲观读后result："+result);
			}finally {
				stampedLock.unlockRead(stamp);
			}
		}
		System.out.println(Thread.currentThread().getName()+"\t"+" finally value: "+result);
	}



}
