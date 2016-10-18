package io.pivotal.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContactWebAppApplication {

//	final static String queueName = "contact-change-event";
//	private static final Logger log = LoggerFactory.getLogger(ContactWebAppApplication.class);
//	@Value("${spring.rabbitmq.host}")
//	String amqp_host;
//	@Value("${spring.rabbitmq.port}")
//	String amqp_port;
//	@Value("${spring.rabbitmq.username}")
//	String amqp_username;
//	@Value("${spring.rabbitmq.password}")
//	String amqp_password;

	public static void main(String[] args) {
		SpringApplication.run(ContactWebAppApplication.class, args);
	}

//	@Bean
//	public Queue contactChangeEventQueue() {
//		log.debug("amqp host: "+amqp_host);
//		log.debug("amqp port: "+amqp_port);
//		log.debug("amqp username: "+amqp_username);
//		log.debug("amqp password: "+amqp_password);
//		return new Queue(queueName);
//	}

}
