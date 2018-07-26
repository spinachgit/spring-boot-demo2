package com.spinach.boot.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.spinach.boot.example.rabbitmq.demo2_work.RabbitSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemo221ApplicationTests {
	@Autowired
	private RabbitSender rabbitSender;

	@Test
	public void send() {
		rabbitSender.send("hello world2",100);
	}



}
