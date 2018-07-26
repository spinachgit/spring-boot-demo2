package com.spinach.boot.example.controller;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spinach.boot.example.rabbitmq.demo1_simple.ConsumerService;
import com.spinach.boot.example.rabbitmq.demo1_simple.SenderService;
import com.spinach.boot.example.rabbitmq.demo2_work.RabbitSender;
import com.spinach.boot.example.rabbitmq.demo3_publish.FanoutSender;
import com.spinach.boot.example.rabbitmq.demo4_route.DirectSender;

@RestController
@RequestMapping("/rabbit")
public class RabbitController {
	@Autowired
	private ConsumerService consumerService;
	@Autowired
	private SenderService senderService;
	@Autowired
	private RabbitSender rabbitSender;
	@Autowired
	private FanoutSender fanoutSender;
	@Autowired
	private DirectSender directSender;
	
	
	@GetMapping("simple")
	public void test0(String msg,int count) throws IOException, TimeoutException, InterruptedException{
		//打开消费者通道
		consumerService.ConsumeMessage();
		//生产者发布消息
		senderService.publishMessage(msg);
	}
	@GetMapping("work")
	public String test1(String msg,int count){
		return rabbitSender.send(msg,count);
	}
	
	@GetMapping("publish")
	public void test2(String msg,int count){
		fanoutSender.send(msg,count);
	}
	
	@GetMapping("route")
	public void test3(String msg,int count){
		directSender.send(msg,count);
	}

}
