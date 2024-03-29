package com.example.spring.lock;

/**
 * @program: myjava
 * @description: 看门狗失效bug case
 * @author: soulx
 * @create: 2022-07-14 09:45
 **/
import java.util.concurrent.locks.Lock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;
import org.springframework.scheduling.annotation.Scheduled;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RedissonLockExpirationRenewalTest {

	private static final String LOCK_KEY = "LOCK_KEY";
	public static final long LOCK_WATCHDOG_TIMEOUT = 1_000L;

	private RedissonClient redisson;

	@Before
	public void before() throws IOException, InterruptedException {
		RedisRunner.startDefaultRedisServerInstance();
		redisson = createInstance();
	}

	@After
	public void after() throws InterruptedException {
		redisson.shutdown();
		RedisRunner.shutDownDefaultRedisServerInstance();
	}

	@Test
	/**
	 * 看门狗失效
	 */
	public void testExpirationRenewalIsWorkingAfterTimeout() throws IOException, InterruptedException {
		{
			RLock lock = redisson.getLock(LOCK_KEY);
			lock.lock();
			try {
				// force expiration renewal error
				restartRedisServer();
				// wait for timeout
				Thread.sleep(LOCK_WATCHDOG_TIMEOUT * 2);
			} finally {
				assertThatThrownBy(lock::unlock).isInstanceOf(IllegalMonitorStateException.class);
			}
		}

		{
			RLock lock = redisson.getLock(LOCK_KEY);
			lock.lock();
			try {
				// wait for timeout
				Thread.sleep(LOCK_WATCHDOG_TIMEOUT * 2);
			} finally {
				lock.unlock();
			}
		}
	}



	private void restartRedisServer() throws InterruptedException, IOException {
		int currentPort = RedisRunner.defaultRedisInstance.getRedisServerPort();
		RedisRunner.shutDownDefaultRedisServerInstance();
		RedisRunner.defaultRedisInstance = new RedisRunner().nosave().randomDir().port(currentPort).run();
	}


	public static Config createConfig() {
		Config config = new Config();
		config.useSingleServer()
				.setAddress(RedisRunner.getDefaultRedisServerBindAddressAndPort());
		config.setLockWatchdogTimeout(LOCK_WATCHDOG_TIMEOUT);
		return config;
	}

	public static RedissonClient createInstance() {
		Config config = createConfig();
		return Redisson.create(config);
	}
}