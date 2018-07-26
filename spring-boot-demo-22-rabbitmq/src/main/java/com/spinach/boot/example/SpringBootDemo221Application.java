package com.spinach.boot.example;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@EnableRabbit
@ServletComponentScan
@SpringBootApplication
public class SpringBootDemo221Application {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemo221Application.class, args);
	}
}
