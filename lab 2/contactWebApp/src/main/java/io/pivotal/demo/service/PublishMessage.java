package io.pivotal.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "contact-change-event")
public class PublishMessage {

	final static String queueName = "contact-change-event";
	private static final Logger log = LoggerFactory.getLogger(PublishMessage.class);

	@Autowired
    private SimpMessagingTemplate template;

	@RabbitHandler
	public void process(@Payload String message) {
		log.debug("process: "+message);
		this.template.convertAndSend("/topic/message", message);
	}
}
