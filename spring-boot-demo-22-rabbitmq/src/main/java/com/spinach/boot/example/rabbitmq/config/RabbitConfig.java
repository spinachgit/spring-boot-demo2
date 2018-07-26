package com.spinach.boot.example.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;

/**
 * amqp 队列配置
 * @author wujing
 */
@Configuration
public class RabbitConfig {
	public final static String queueName = "spinach.queue";
	public final static String HOST = "localhost";
	public final static String USERNAME = "guest";
	public final static String PASSWORD = "guest";
	public final static int PORT = 5672;

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	/**
	 * 配置消息队列模版 并且设置MessageConverter为自定义FastJson转换器
	 * @param connectionFactory
	 * @return
	 */
	//@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(new Jackson2JsonMessageConverter());
		//template.setMessageConverter(new RabbitMqFastJsonConverter());
		return template;
	}

	/**
	 * 自定义队列容器工厂 并且设置MessageConverter为自定义Jackson2JsonMessageConverter转换器
	 * @param connectionFactory
	 * @return
	 */
	//@Bean
	public SimpleRabbitListenerContainerFactory messageContainer(CachingConnectionFactory connectionFactory) {
		// String username = myConnectionFactory.getUsername();
		// ConnectionFactory rabbitConnectionFactory = myConnectionFactory.getRabbitConnectionFactory();
		/*
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		factory.setDefaultRequeueRejected(false);
		*/
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		//factory.setMessageConverter(new RabbitMqFastJsonConverter());
		factory.setErrorHandler(new ConditionalRejectingErrorHandler(new FatalExceptionStrategy() {
			@Override
			public boolean isFatal(Throwable t) {
				if (t instanceof ListenerExecutionFailedException && (t.getCause() instanceof MessageConversionException
						|| t.getCause() instanceof MethodArgumentNotValidException)) {
					System.out.println("消息格式不对,忽略");
					return true;// return true则会从broker删除该消息
				}
				return false;
			}
		}));
		return factory;
	}
}
