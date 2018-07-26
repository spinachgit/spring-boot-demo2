package com.spinach.boot.example.rabbitmq.demo3_publish;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
 
@Component
public class FanoutSender {
	@Autowired
	private AmqpTemplate rabbitTemplate;
 
	public void send(String context,int count) {
		if(StringUtils.isEmpty(context)){
			context = "hi, fanout msg!";
		}
		System.out.println("Sender信息 : " + context+",和次数"+count);
		for(int i=0;i<count;i++){
			this.rabbitTemplate.convertAndSend(FanoutRabbitConfig.exchangeName,"", context+i);
			//手动的写法，原来的：channel绑定指定的交换机
			//channel.exchangeDeclare("exchangeName", BuiltinExchangeType.FANOUT);
		}
	}
}