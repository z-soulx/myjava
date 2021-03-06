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
			System.out.println("rocketMQConsumer  ??????------");
			// ????????????
			// ?????????????????????consumer????????????????????????
			DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("broker-a");
			// ???????????????NameServer??????????????????????????????????????????
			consumer.setNamesrvAddr("127.0.0.1:9876");
			// ??????consumer???????????????
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			// ?????????????????????????????????????????????
			consumer.setMessageModel(MessageModel.CLUSTERING);
			// ????????????????????????????????????16???
			consumer.setMaxReconsumeTimes(5);
			// ??????consumer????????????Topic???Tag???*???????????????Tag
			consumer.subscribe("GD_runmode_syncRunmodeRecloseInfo", "*");
			// Listener????????????????????????????????????,??????topic???????????????????????????????????????
			consumer.registerMessageListener(new MessageListenerConcurrently() {
				@Override
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
					// ????????????????????????????????????
					try {
						if (msgs != null && msgs.size() > 0) {
							MessageExt messageExt = msgs.get(0);
							String msgBody = new String(messageExt.getBody(), "utf-8");
							System.out.println(" ????????????????????????" + msgBody);
						}
					} catch (Exception e) {
						System.out.println("?????????????????????????????????????????????");
						e.printStackTrace();
						// ????????????????????????????????????????????????????????????????????????????????????????????????????????????
						if (msgs.get(0).getReconsumeTimes() == 3) {
							System.out.println("?????????????????????" + msgs);
							return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
						} else {
							// ???????????????????????????????????????
							// System.out.println("??????????????????????????????????????????");
							System.out.println("?????????????????????????????????????????????");
							return ConsumeConcurrentlyStatus.RECONSUME_LATER;
						}
					}
					System.out.println("???????????????????????????");
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});
			// ??????start()????????????consumer
			consumer.start();
			System.out.println("??????????????????????????????");
			System.out.println("rocketMQConsumer ??????------");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("????????????????????????--" + e.getMessage());
		}
	}


	}



