package com.example.spring.service.mq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.impl.consumer.ConsumeMessageConcurrentlyService;
import com.alibaba.rocketmq.client.impl.consumer.ConsumeMessageService;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.List;

import java.util.Properties;
import java.util.function.Consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.junit.Test;

/**
 * @progr myjava
 * @descripti
 * @auth soulx
 * @crea 2022-03-09 01
 **/
public class MQTest {

	public static void main(String[] args)
			throws UnsupportedEncodingException, MQClientException, RemotingException, InterruptedException, MQBrokerException {
		DefaultMQProducer p = new DefaultMQProducer();
		DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
		          producer.start();
		Message msg =
				                    new Message("TopicTestjjj", "2", "KEY", ("Hello RocketMQ " ).getBytes(RemotingHelper.DEFAULT_CHARSET));
		producer.send(msg);
		SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                     @Override
                     public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
				                         Integer id = (Integer) arg;
				                         int index = id % mqs.size();
				                         return mqs.get(index);
				                     }
                 }, "");


	}

	@Test
	public  void  consum(){
		try {
			System.out.println("rocketMQConsumer  开始------");
			// 消费目标
			// 声明一个消费者consumer，需要传入一个组
			DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("broker-a");
			// 设置集群的NameServer地址，多个地址之间以分号分隔
			consumer.setNamesrvAddr("127.0.0.1:9876");
			// 设置consumer的消费策略
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			// 集群模式消费，广播消费不会重试
			consumer.setMessageModel(MessageModel.CLUSTERING);
			// 设置最大重试次数，默认是16次
			consumer.setMaxReconsumeTimes(5);
			// 设置consumer所订阅的Topic和Tag，*代表全部的Tag
			consumer.subscribe("GD_runmode_syncRunmodeRecloseInfo", "*");
			// Listener，主要进行消息的逻辑处理,监听topic，如果有消息就会立即去消费
			consumer.registerMessageListener(new MessageListenerConcurrently() {
				@Override
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
					// 获取第一条消息，进行处理
					try {
						if (msgs != null && msgs.size() > 0) {
							MessageExt messageExt = msgs.get(0);
							String msgBody = new String(messageExt.getBody(), "utf-8");
							System.out.println(" 接收消息整体为：" + msgBody);
						}
					} catch (Exception e) {
						System.out.println("消息消费失败，请尝试重试！！！");
						e.printStackTrace();
						// 尝试重新消费，直接第三次如果还不成功就放弃消费，进行消息消费失败补偿操作
						if (msgs.get(0).getReconsumeTimes() == 3) {
							System.out.println("消息记录日志：" + msgs);
							return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
						} else {
							// 重试状态码，重试机制可配置
							// System.out.println("消息消费失败，尝试重试！！！");
							System.out.println("消息消费失败，请尝试重试！！！");
							return ConsumeConcurrentlyStatus.RECONSUME_LATER;
						}
					}
					System.out.println("消息消费成功！！！");
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});
			// 调用start()方法启动consumer
			consumer.start();
			System.out.println("消费者启动成功。。。");
			System.out.println("rocketMQConsumer 结束------");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("消息消费操作失败--" + e.getMessage());
		}
	}


	}



