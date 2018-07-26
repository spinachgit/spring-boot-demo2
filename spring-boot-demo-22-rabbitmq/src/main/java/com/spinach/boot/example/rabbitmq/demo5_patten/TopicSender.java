package com.spinach.boot.example.rabbitmq.demo5_patten;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.spinach.boot.example.rabbitmq.demo3_publish.FanoutRabbitConfig;

@Component
public class TopicSender {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(String context, int count) {
		if (StringUtils.isEmpty(context)) {
			context = "hi, fanout msg!";
		}
		System.out.println("Sender信息 : " + context + ",和次数" + count);
		String message = "";
		for (int i = 0; i < count; i++) {
			message = context+i;
			if (i % 2 == 0) {
				this.rabbitTemplate.convertAndSend(TopicRabbitConfig.exchangeName,"even",message);
				
				this.rabbitTemplate.send(TopicRabbitConfig.exchangeName, "even", new Message(message.getBytes(),new MessageProperties()));
				//发布模式
				System.out.println("------ 发布模式 -------");
				this.rabbitTemplate.setExchange(FanoutRabbitConfig.exchangeName);
				this.rabbitTemplate.send("", new Message(message.getBytes(), new MessageProperties()));
			} else {
				this.rabbitTemplate.convertAndSend(TopicRabbitConfig.exchangeName, "odd", message);
			}
			// 手动的写法，原来的：channel绑定指定的交换机
			// channel.exchangeDeclare("exchangeName", BuiltinExchangeType.FANOUT);
		}
	}
}