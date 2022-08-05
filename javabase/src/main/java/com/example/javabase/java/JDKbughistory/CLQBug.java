package com.example.javabase.java.JDKbughistory;

import java.lang.reflect.Field;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.Test;

/**
 * @program: myjava
 * @description: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=8137185
 *  memory leak，内存泄漏。
 * @author: soulx
 * @create: 2022-08-03 13:48
 **/
public class CLQBug {
	public static void main(String[] args)
	{
		ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<>();
		queue.offer(new Object());

		Object item = new Object();

		long iterations = 0;
		try
		{
			while (true)
			{
				++iterations;
				queue.offer(item);
				queue.remove(item);
			}
		}
		catch (OutOfMemoryError e)
		{
			queue = null;
			System.err.println("iterations: " + iterations);
			throw e;
		}
	}


	@Test
	public void test2()
	{
		ConcurrentLinkedQueue<Object> queue=new ConcurrentLinkedQueue<Object>();
		queue.add(new Object()); //Required for the leak to appear.
		Object object=new Object();
		int loops=0;
		Runtime rt=Runtime.getRuntime();
		long last=System.currentTimeMillis();
		while(true)
		{
			if(loops%10000==0)
			{
				long now=System.currentTimeMillis();
				long duration=now-last;
				last=now;
				System.err.printf("duration=%d q.size=%d memory max=%d free=%d total=%d%n",duration,queue.size(),rt.maxMemory(),rt.freeMemory(),rt.totalMemory());
			}
			queue.add(object);
			queue.remove(object);
			++loops;
		}
	}

	/**
	 * https://www.cnblogs.com/ocean234/p/10779784.html
	 * 验证run 模式 ConcurrentLinkedQueue 首次 插入没有被改变
	 */
	@Test
	public void test(){
		ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
		print(queue);
		queue.offer("aaa");
		print(queue);
		queue.offer("bbb");
		print(queue);
		queue.offer("ccc");
		print(queue);
	}

	/**
	 * 打印并发队列head属性的identityHashCode
	 * @param queue
	 */
	private static void print(ConcurrentLinkedQueue queue) {
		Field field = null;
		boolean isAccessible = false;
		try {
			field = ConcurrentLinkedQueue.class.getDeclaredField("head");
			isAccessible = field.isAccessible();
			if (!isAccessible) {
				field.setAccessible(true);
			}
			System.out.println("head: " + System.identityHashCode(field.get(queue)));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			field.setAccessible(isAccessible);
		}
	}
}
