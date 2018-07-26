package com.spinach.boot.example.rabbitmq.demo2_work;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitSender {
	@Autowired
	private AmqpTemplate amqpTemplate;
	public static final String queueName = "queue.work";
	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}
	
	public String send(String msg, int count) {
		for(int i=0;i<count;i++){
			this.amqpTemplate.convertAndSend(queueName, msg);
		}
		return "发送消息"+msg+"和次数："+count;
	}
}
