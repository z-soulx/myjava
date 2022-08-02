package com.example.spring.lock;

import com.example.spring.task.Task;
import java.util.concurrent.locks.Lock;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @program: myjava
 * @description: redis的分布式锁
 * @author: soulx
 * @create: 2022-07-13 19:28
 **/
public class RedisLock {
	static RedissonClient redissonClient = Redisson.create();
	public static void main(String[] args) {

		RLock lock = redissonClient.getLock("guodong");

		// 具有Watch Dog 自动延期机制 默认续30s
		lock.lock();
		System.out.println("ss");
		lock.unlock();
	}

	private static Logger LOG = LoggerFactory.getLogger(RedisLock.class);

	/**
	 * 死锁
	 * /*
	 * 	 * This task just start a critical section locked by
	 * 	 * Redisson Distributed Lock.
	 * 	 * While this task is idle, run this on redis:
	 * 	 * CLIENT PAUSE 5000  主要还是模拟 Redis 处理请求超时的情况，就是让 Redis 假死 5s，这样程序发过来的请求就会超时。
	 * 	 * The above pause will cause a RedisResponseTimeoutException
	 *
	 * @throws InterruptedException
	 */
	@Scheduled(fixedDelay = 1000)
	public void run() throws InterruptedException {
		Lock lock = redissonClient.getLock("LOCK_TEST");
		LOG.debug("LOCKING...");
		lock.lock();
		LOG.debug("LOCKED!");
		try {
			LOG.info("TASK RUN...");
			Thread.sleep(2000);
		} finally {
			LOG.debug("UNLOCKING...");
			lock.unlock();
			LOG.debug("UNLOCKED!");
		}
	}

}
