package io.pivotal.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContactWebAppApplication {

	private static final Log log = LogFactory.getLog(ContactWebAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ContactWebAppApplication.class, args);
	}

}
