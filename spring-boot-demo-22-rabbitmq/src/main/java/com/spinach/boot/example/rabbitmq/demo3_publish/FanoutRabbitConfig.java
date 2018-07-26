package com.spinach.boot.example.rabbitmq.demo3_publish;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Direct：direct 类型的行为是"先匹配, 再投送". 即在绑定时设定一个 routing_key, 消息的routing_key 匹配时, 才会被交换器投送到绑定的队列中去.
 * Topic：按规则转发消息（最灵活）
 * Headers：设置header attribute参数类型的交换机
 * Fanout：转发消息到所有绑定队列
 * @author:whh
 * @date:2018年7月17日下午5:09:05
 */
@EnableRabbit
@Configuration
public class FanoutRabbitConfig {
	public static final String queueNameA= "queue.publish.A";
	public static final String queueNameB= "queue.publish.B";
	public static final String exchangeName= "fanoutExchange";
	
	@Bean
	public Queue AMessage() {
		return new Queue(queueNameA);
	}

	@Bean
	public Queue BMessage() {
		return new Queue(queueNameB);
	}

	@Bean
	FanoutExchange fanoutExchange() {
		return new FanoutExchange(exchangeName);
	}

	@Bean
	Binding bindingExchangeA(Queue AMessage, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(AMessage).to(fanoutExchange);
	}

	@Bean
	Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(BMessage).to(fanoutExchange);
	}
}