package com.spinach.boot.example.rabbitmq.demo5_patten;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
 
@Component
//@RabbitListener(queues = TopicRabbitConfig.queueNameA)
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value=TopicRabbitConfig.queueNameA),
        exchange = @Exchange(value = TopicRabbitConfig.exchangeName),
        key = "odd")
  )
public class TopicReceiver {
 
	protected static Logger logger=LoggerFactory.getLogger(TopicReceiver.class); 
	
	@RabbitHandler()
    public void processA(String message) {
        System.out.println("topic Receiver AAA  : " + message);
    }
	
	
	@RabbitListener(bindings = @QueueBinding(
	        value = @Queue(value=TopicRabbitConfig.queueNameB),
	        exchange = @Exchange(value = TopicRabbitConfig.exchangeName),
	        key = "even"))
	public void processB(String message) {
		System.out.println("topic Receiver BBB  : " + message);
	}
}