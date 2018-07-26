package com.spinach.boot.example.rabbitmq.demo2_work;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitSender.queueName)
public class RabbitReceiver {
	/**
	 * 一个：@RabbitListener，对应一个：@RabbitHandler，
	 * 如果有多个RabbitHandler：会识别不了对应哪个方法。Ambiguous methods for payload type
	 * @author:whh
	 * @date:2018年7月17日下午2:26:54
	 * @param text
	 * @throws InterruptedException
	 */
	@RabbitHandler
	public void receiveQueue(String text) throws InterruptedException {
		System.out.println("work模式：消息者111：" + text);
		Thread.sleep(100);
	}
	
	@RabbitListener(queues = "queue.work")
	public void receiveQueue2(String text) throws InterruptedException {
		System.out.println("work模式：消息者222：" + text);
		Thread.sleep(180);
	}
	
	@RabbitListener(queues = "queue.work")
	public void receiveQueue3(String text) throws InterruptedException {
		System.out.println("work模式：消息者333：" + text);
		Thread.sleep(320);
	}
}