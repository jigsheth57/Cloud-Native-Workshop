package io.pivotal.demo;

import io.pivotal.demo.common.ContactBootstrap;
import io.pivotal.demo.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableSwagger2
@Controller
@EnableConfigurationProperties
public class ContactDataServiceApplication {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ContactRepository contactRepo;

	public static void main(String[] args) {
		SpringApplication.run(ContactDataServiceApplication.class, args);
	}

	@Configuration
	public static class RepositoryConfig extends RepositoryRestConfigurerAdapter {
		@Override
		public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
			config.exposeIdsFor(io.pivotal.demo.domain.Contact.class);
		}
	}

	@Bean
	public ContactBootstrap contactBootstrap() { return new ContactBootstrap();}

	@PostConstruct
	public void bootstrap() {
		if (contactRepo.count() == 0) {
			contactRepo.save(contactBootstrap().getContacts().get(0));
		}
	}


	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("io.pivotal.demo.controller"))
//				.paths(PathSelectors.regex("^/(contacts|contact|manage).*$"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("SFDC Auth Service API sample")
				.description("SFDC Auth Service API demo using Spring Cloud Services and SFDC Api")
				.termsOfServiceUrl("http://pivotal.io/")
				.contact(new Contact("Jignesh Sheth",null,null))
				.license("Apache License Version 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
				.version("2.0")
				.build();
	}

	@RequestMapping("/")
	public String home() {
		return "redirect:/swagger-ui.html";
	}
}
