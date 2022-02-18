package com.example.javabase.java.ConsumClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: myjava
 * @description: 自定义 ArrayBlockingQueue
 * @see ArrayBlockingQueue
 * @author: soulx
 * @create: 2022-02-06 15:10
 **/
public class CustomArrayListBlockQueue<E> {
	  List<E> list = new ArrayList<>();
		Lock lock = new ReentrantLock();

				Condition empty;
		Condition full;
		public CustomArrayListBlockQueue() {
			 empty = this.lock.newCondition();
			full = this.lock.newCondition();
		}
   // 阻塞队列 接口  take put 而非 offer poll


		public void offer() throws InterruptedException {
			lock.lock(); // 只有获取锁才能 await
//			if(list.size() == 满了)
			full.await();
//			list.add();
			empty.signal();
		}

		public void poll() throws InterruptedException {
			lock.lock();
			if (list.size() == 0) {
				empty.await();
			}
//			list.remove()
			full.signal();
		}




}
