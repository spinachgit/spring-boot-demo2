package com.spinach.boot.example.rabbitmq.demo4_route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
 
@Component
//@RabbitListener(queues = DirectRabbitConfig.queueNameA)
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value=DirectRabbitConfig.queueNameA),
        exchange = @Exchange(value = DirectRabbitConfig.exchangeName),
        key = "odd")
  )
public class DirectReceiver {
 
	protected static Logger logger=LoggerFactory.getLogger(DirectReceiver.class); 
	
	@RabbitHandler()
    public void processA(String message) {
        System.out.println("direct Receiver AAA  : " + message);
    }
	
	
	@RabbitListener(bindings = @QueueBinding(
	        value = @Queue(value=DirectRabbitConfig.queueNameB),
	        exchange = @Exchange(value = DirectRabbitConfig.exchangeName),
	        key = "even")
	  )
	public void processB(String message) {
		System.out.println("direct Receiver BBB  : " + message);
	}
}