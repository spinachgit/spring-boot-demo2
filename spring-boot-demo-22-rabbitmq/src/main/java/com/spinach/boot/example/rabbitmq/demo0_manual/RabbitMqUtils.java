package com.spinach.boot.example.rabbitmq.demo0_manual;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 创建工厂类--rabbitMq
 * @author:whh
 * @date:2018年7月17日上午11:27:15
 */
public class RabbitMqUtils {
	private static String host = "127.0.0.1";
	private static Integer port = 5672;
	private static String userName = "guest";
	private static String pwd = "guest";
	private static String vhost = "/";
	private static Connection connection;
	private static Channel channel;

	public synchronized static Connection getSingleConnection() throws IOException, TimeoutException {
		if(connection==null){
			// 1 定义链接工厂
			ConnectionFactory factory = new ConnectionFactory();
			// 2 设置服务器地址，端口，账户信息，用户名，密码，vhost
			factory.setHost(host);
			factory.setPort(port);
			factory.setVirtualHost(vhost);
			factory.setUsername(userName);
			factory.setPassword(pwd);
			// 3 通过工厂获取链接
			connection = factory.newConnection();
		}
		return connection;
	}
	
	public static Channel getNewChannel() throws IOException, TimeoutException{
		Connection connection = RabbitMqUtils.getSingleConnection();
		Channel channel = connection.createChannel();
		return channel;
	}
	public static Channel getSingleChannel() throws IOException, TimeoutException{
		Connection connection = RabbitMqUtils.getSingleConnection();
		if(channel == null){
			channel = connection.createChannel();
		}
		return channel;
	}
}