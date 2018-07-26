package com.spinach.boot.example.rabbitmq.demo4_route;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Fanout：转发消息到所有绑定队列:发布模式
 * Direct：direct 类型的行为是"先匹配, 再投送". 即在绑定时设定一个 routing_key, 消息的routing_key 匹配时, 才会被交换器投送到绑定的队列中去.
 * Topic：按规则转发消息（最灵活）
 * Headers：设置header attribute参数类型的交换机
 * @author:whh
 * @date:2018年7月17日下午5:09:05
 */
@Configuration
public class DirectRabbitConfig {
	public static final String queueNameA= "queue.route.A";
	public static final String queueNameB= "queue.route.B";
	public static final String exchangeName= "directExchange";
	@Bean
	public Queue RouteMessageA() {
		return new Queue(queueNameA);
	}

	@Bean
	public Queue RouteMessageB() {
		return new Queue(queueNameB);
	}

	@Bean
	DirectExchange DirectExchange() {
		return new DirectExchange(exchangeName);
	}

}