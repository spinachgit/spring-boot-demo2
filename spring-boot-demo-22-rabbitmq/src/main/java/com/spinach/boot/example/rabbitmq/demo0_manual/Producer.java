package com.spinach.boot.example.rabbitmq.demo0_manual;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {
	private static String QUEUE_NAME = "queue.manual";
	/**
	 * 生产者
	 * @author SHF
	 * @version 创建时间：2018年7月3日 上午10:08:57
	 * @throws TimeoutException
	 * @throws IOException
	 */
	public static void producer() throws IOException, TimeoutException {
		// 1 获取链接以及mq通道
		Connection connection = RabbitMqUtils.getSingleConnection();
		Channel channel = connection.createChannel();
		// 2 申明队列
		DeclareOk queueOK = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		// 3 添加消息内容
		String message = "Hello World; 施爷";
		for(int i=0;i<50;i++){
			channel.basicPublish("", QUEUE_NAME, null, (message+i).getBytes());
		}
		System.out.println("sent: '" + message + "'");
		// 关闭通道和连接
		channel.close();
		connection.close();
	}
	public static void main(String[] args) throws IOException, TimeoutException {
		producer();
	}
}
