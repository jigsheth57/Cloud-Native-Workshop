package io.pivotal.demo;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class ContactDataServiceApplication {

	final static String queueName = "contact-change-event";
	
	public static void main(String[] args) {
		SpringApplication.run(ContactDataServiceApplication.class, args);
	}
		
	@Bean
	public Queue contactChangeEventQueue() {
		return new Queue(queueName);
	}
}
