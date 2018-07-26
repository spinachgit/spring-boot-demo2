package com.spinach.boot.example.rabbitmq.demo0_manual;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Consumer {
	private static String QUEUE_NAME = "queue.manual";

	public static void main(String[] args) throws Exception {
		Consum();
	}

	/**
	 * 消费者
	 * @author SHF
	 * @version 创建时间：2018年7月3日 上午11:03:25
	 * @throws Exception
	 */
	public static void Consum() throws Exception {
		// 1 获取链接及mq通道,
		/*Connection connection = RabbitMqUtils.getSingleConnection();
		Channel channel = connection.createChannel();*/
		Channel channel = RabbitMqUtils.getSingleChannel();
		// 2 申明队列(如果存在就不创建)
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		/*// 3 定义队列的消费者
		QueueingConsumer consumer = new QueueingConsumer(channel); //spring-amqp-1.7.4
		// 4 监听队列
		channel.basicConsume(QUEUE_NAME, true, consumer);
		
		// 5 获取消息
		while (true) {
			Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" received: " + message);
			Thread.sleep(1000);
		}*/
		// 创建消息者
		com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
			// 处理消息
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
		// 监听队列
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}
}
