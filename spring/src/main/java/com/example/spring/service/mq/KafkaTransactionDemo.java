package com.example.spring.service.mq;



import com.google.common.collect.Maps;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * @program: myjava
 * @description: kafka事务demo
 *
 * 在Kafka事务中，一个原子性操作，根据操作类型可以分为3种情况。情况如下：
 *
 * 只有Producer生产消息，这种场景需要事务的介入；
 * 消费消息和生产消息并存，比如Consumer&Producer模式，这种场景是一般Kafka项目中比较常见的模式，需要事务介入。Kafka的事务主要用来处理consume-process-produce场景的原子性问题；
 * 只有Consumer消费消息，这种操作在实际项目中意义不大，和手动Commit Offsets的结果一样，而且这种场景不是事务的引入目的。
 * ————————————————
 * 版权声明：本文为CSDN博主「PONY LEE」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/weixin_38251332/article/details/106279744
 * @author: soulx
 * @create: 2022-03-24 13:44
 **/

public class KafkaTransactionDemo {

	public static void main(String[] args) {
		new KafkaTransactionDemo().consumerTransformProducer();
	}

	/**
	 * 在一个事务只有生产消息操作
	 */
	public void onlyProducerInTransaction() {
		Producer producer = buildProducer();
		// 1.初始化事务
		producer.initTransactions();
		// 2.开启事务
		producer.beginTransaction();
		try {
			// 3.kafka写操作集合
			// 3.1 do业务逻辑
			// 3.2 发送消息
			producer.send(new ProducerRecord<String, String>("test.1", "transaction-data-1"));
			// 3.3 do其他业务逻辑,还可以发送其他topic的消息。

			// 4.事务提交
			producer.commitTransaction();
		} catch (Exception e) {
			// 5.放弃事务
			producer.abortTransaction();

		}
	}

	/**
	 * 创建一个事务，在这个事务操作中，只有生成消息操作，如下代码。
	 * 这种操作其实没有什么意义，跟使用手动提交效果一样，无法保证消费消息操作和提交偏移量操作在一个事务
	 */
	public void onlyConsumerInTransaction() {
		// 1.构建上产者
		Producer producer = buildProducer();
		// 2.初始化事务(生成productId),对于一个生产者,只能执行一次初始化事务操作
		producer.initTransactions();
		// 3.构建消费者和订阅主题
		Consumer consumer = buildConsumer();
		consumer.subscribe(Arrays.asList("test.1"));

		while (true) {
			// 4.开启事务
			producer.beginTransaction();
			// 5.1 接受消息
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(100));

			try {
				// 5.2 do业务逻辑;
				System.out.println("customer Message---");
				Map<TopicPartition, OffsetAndMetadata> commits = Maps.newHashMap();

				for (ConsumerRecord<String, String> record : records) {
					// 5.2.1 读取消息,并处理消息。print the offset,key and value for the consumer records.
					// 此处为真正的处理消息，与步骤7的提交偏移量不在一个事务里面
					System.out.printf(Thread.currentThread().getName() + ": partition = %d, offset = %d, key = %s, value = %s, timestamp = %s,timestampType = %s %n", record.partition(), record.offset(), record.key(), record.value(), record.timestamp(), record.timestampType());

					// 5.2.2 记录提交的偏移量
					commits.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));
				}
				// 7.提交偏移量
				producer.sendOffsetsToTransaction(commits, "test_group0111");

				// 8.事务提交
				producer.commitTransaction();
			} catch (Exception e) {
				// 7.放弃事务
				producer.abortTransaction();
			}
		}
	}
	/**
	 * 消费-生产并存（consume-transform-produce）
	 * 在一个事务中，既有生产消息操作又有消费消息操作，即常说的Consume-tansform-produce模式
	 * 这是kafka事务最能实现价值的方式
	 */
	public void consumerTransformProducer() {
		// 1.构建上产者
		Producer producer = buildProducer();
		// 2.初始化事务(生成productId),对于一个生产者,只能执行一次初始化事务操作
		producer.initTransactions();

		// 3.构建消费者和订阅主题
		Consumer consumer = buildConsumer();
		consumer.subscribe(Arrays.asList("test.1"));

		while (true) {
			// 4.开启事务
			producer.beginTransaction();
			// 5.1 接受消息
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(100));

			try {
				// 5.2 do业务逻辑;
				System.out.println("customer Message---");
				Map<TopicPartition, OffsetAndMetadata> commits = Maps.newHashMap();

				for (ConsumerRecord<String, String> record : records) {
					// 5.2.1 打印消息。print the offset,key and value for the consumer records.
					System.out.printf(Thread.currentThread().getName() + ": partition = %d, offset = %d, key = %s, value = %s, timestamp = %s,timestampType = %s %n", record.partition(), record.offset(), record.key(), record.value(), record.timestamp(), record.timestampType());

					// 5.2.2 记录提交的偏移量
					commits.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));

					// 6.生产新的消息。比如外卖订单状态的消息,如果订单成功,则需要发送跟商家结转消息或者派送员的提成消息
					// 此处为真正的处理消息，与步骤7的提交偏移量在一个事务里面
					producer.send(new ProducerRecord<String, String>("test.2", "tran-" + record.value()));

				}
				// 7.提交偏移量
				producer.sendOffsetsToTransaction(commits, "test_group0111");

				// 8.事务提交，同时成功
				producer.commitTransaction();
			} catch (Exception e) {
				// 7.放弃事务，同时失败
				producer.abortTransaction();
			}
		}
	}
	/**
	 * 幂等性测试,无事务
	 */
	public void consumerTransformProducerByIdempotence() {
		// 1.构建上产者
		Producer producer = buildProducer();

		// 3.构建消费者和订阅主题
		Consumer consumer = buildConsumer();
		consumer.subscribe(Arrays.asList("test.1"));

		while (true) {
			// 5.1 接受消息
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(100));

			try {
				// 5.2 do业务逻辑;
				System.out.println("Processing Message ---");
				Map<TopicPartition, OffsetAndMetadata> commits = Maps.newHashMap();

				for (ConsumerRecord<String, String> record : records) {
					// 5.2.1 读取消息,并处理消息。print the offset,key and value for the consumer records.
					System.out.printf(Thread.currentThread().getName() + ": partition = %d, offset = %d, key = %s, value = %s, timestamp = %s,timestampType = %s %n", record.partition(), record.offset(), record.key(), record.value(), record.timestamp(), record.timestampType());

					// 5.2.2 记录提交的偏移量
					commits.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));

					// 6.生产新的消息。比如外卖订单状态的消息,如果订单成功,则需要发送跟商家结转消息或者派送员的提成消息
					producer.send(new ProducerRecord<String, String>("test.2", "idem-" + record.value()));

				}
				// 7.提交偏移量，不能做到一个事务里面，不能做到同时成功和同时失败
				consumer.commitSync();

			} catch (Exception e) {
			}
		}
	}

	/**
	 * 需要:
	 * 1、设置transactional.id
	 * 2、设置enable.idempotence
	 *
	 * @return
	 */

	private Producer buildProducer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.1.8:9092");
		props.put("client.id", "producer_01");
//        props.put("acks", "all");
		props.put("enable.idempotence", true); // 设置了幂等性为true则会自动将acks设置成all，如果强制设置成非all，则会报错
		props.put("transactional.id", "tran1"); //设置了事务 幂等性自动设置为true，如果强制设置成非true则会报错
		props.put("retries", 3);
		props.put("max.in.flight.requests.per.connection", 5);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("compression.type", "gzip");//none, gzip, snappy, 或者 lz4. 默认none
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("partitioner.class", "com.kafka.CustomPartitioner");
		KafkaProducer<String, String> producer = new KafkaProducer<>(props);
		return producer;
	}

	/**
	 * 需要:
	 * 1、关闭自动提交 enable.auto.commit 设置为 false，会在下游producer完成以后，commit offset
	 * 2、isolation.level为 read_committed, 主要是为了处理上游的producer是事务型的提交
	 *
	 * @return
	 */

	public Consumer buildConsumer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.1.8:9092");
		props.put("group.id", "test_group0111");
		props.put("client.id", "consumer_01");
		// 设置隔离级别
		props.put("isolation.level", "read_committed");
		// 关闭自动提交
		props.put("enable.auto.commit", "false");
		props.put("auto.commit.interval.ms", "1000");
		props.put("max.poll.interval.ms", "300000");
		// 在没有offset的情况下采取的拉取策略，[latest, earliest, none]
		props.put("auto.offset.reset", "latest");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//            props.put("value.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		return consumer;
	}

}


