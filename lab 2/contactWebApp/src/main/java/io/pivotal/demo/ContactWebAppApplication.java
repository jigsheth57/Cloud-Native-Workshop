package io.pivotal.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContactWebAppApplication {

	final static String queueName = "contact-change-event";
	private static final Logger logger = LoggerFactory.getLogger(ContactWebAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ContactWebAppApplication.class, args);
	}

	@Bean
	public Queue contactChangeEventQueue() {
		return new Queue(queueName);
	}

}
