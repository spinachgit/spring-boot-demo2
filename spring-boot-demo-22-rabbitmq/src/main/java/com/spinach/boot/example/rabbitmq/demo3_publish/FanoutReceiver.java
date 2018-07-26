package com.spinach.boot.example.rabbitmq.demo3_publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
 
@Component
@RabbitListener(queues = FanoutRabbitConfig.queueNameA)
public class FanoutReceiver {
 
	protected static Logger logger=LoggerFactory.getLogger(FanoutReceiver.class); 
	
	@RabbitHandler
    public void processA(String message) {
        System.out.println("fanout Receiver AAA  : " + message);
    }
	
	@RabbitHandler
    public void processA2(byte[] msg){
        System.out.println("fanout Receiver AAA  : "+new String(msg));
    }
	
	@RabbitListener(queues = FanoutRabbitConfig.queueNameB)
	public void processB(String message) {
		System.out.println("fanout Receiver BBB  : " + message);
	}
}