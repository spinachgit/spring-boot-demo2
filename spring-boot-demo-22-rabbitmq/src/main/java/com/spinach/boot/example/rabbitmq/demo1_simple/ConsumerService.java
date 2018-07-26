package com.spinach.boot.example.rabbitmq.demo1_simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 消息者
 * @author:whh
 * @date:2018年7月17日上午11:56:20
 */
@Service
public class ConsumerService {
	private static final String QUEUE_NAME = "queue.simple";
	@Resource
	private RabbitMqFactory factory;

	public void ConsumeMessage() throws IOException, TimeoutException, InterruptedException {
		//获得通道
		Channel channel = factory.getChannel();
		//声明队列
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		
		//channel.queueBind(QUEUE_NAME, exchange_name, "")
		
		//创建消息者
		Consumer consumer = new DefaultConsumer(channel) {
			//处理消息
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				// ack
				try {
					System.out.println(" [x] Received '" + message + "'");
					channel.basicAck(envelope.getDeliveryTag(), false);
				} catch (Exception e) {
					channel.basicNack(envelope.getDeliveryTag(), false, true);
				}
			}
		};
		
		//监听队列
		channel.basicConsume(QUEUE_NAME, false, consumer);
		Thread.sleep(100L);
	}
}