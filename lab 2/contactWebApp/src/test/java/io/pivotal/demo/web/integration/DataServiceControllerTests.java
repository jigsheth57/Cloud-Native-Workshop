package io.pivotal.demo.web.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.pivotal.demo.ContactWebAppApplication;
import io.pivotal.demo.domain.Contact;
import junit.framework.TestCase;

/**
 * Integration tests for the <code>DataServiceController</code> class.
 * 
 * The <code>SpringApplicationConfiguration</code>
 * annotation ensures that the proper configuration (i.e.
 * embedded database and data source) is applied.  The 
 * <code>IntegrationTest</code> annotation starts the 
 * embedded Tomcat server for the controller.
 * 
 * @author Jignesh Sheth
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContactWebAppApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class DataServiceControllerTests {

	private static final Log log = LogFactory.getLog(DataServiceControllerTests.class);
	
	@Autowired
	EmbeddedWebApplicationContext server;
	
	private MockMvc mvc;
	
	/**
	 * Sets up this test suite.
	 */
	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(server).build();
	}

	/**
	 * Tests the get all contacts method of the controller.
	 */
	@Test
	public void testGetAllContacts() {
		try {
			this.mvc.perform(
					MockMvcRequestBuilders.get("/contacts"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
					.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.contacts").isArray());
		} catch (Exception e) {
			log.error(e);
			TestCase.fail(e.getMessage());
		}
	}
	
	/**
	 * Tests the get contact by id method of the controller.
	 */
	@Test
	public void testGetContactById() {
		try {
			this.mvc.perform(
					MockMvcRequestBuilders.get("/contacts/1"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
					.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasEntry("firstName", "Jig")));
		} catch (Exception e) {
			log.error(e);
			TestCase.fail(e.getMessage());
		}
	}

	/**
	 * Tests the save contact method of the controller.
	 */
	@Test
	public void testSaveContact() {
		Contact contact = new Contact("title", "firstName", "lastName", "email", "phone");
		try {
			this.mvc.perform(
					MockMvcRequestBuilders.post("/contacts")
					.contentType("application/json")
					.content(new ObjectMapper().writeValueAsString(contact))
			)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasEntry("firstName", "firstName")));
		} catch (Exception e) {
			log.error(e);
			TestCase.fail(e.getMessage());
		}
	}

	/**
	 * Tests the delete contact method of the controller.
	 */
	@Test
	public void testDeleteContact() {
		try {
			this.mvc.perform(
					MockMvcRequestBuilders.delete("/contacts/1"))
					.andExpect(MockMvcResultMatchers.status().isOk());
					
		} catch (Exception e) {
			log.error(e);
			TestCase.fail(e.getMessage());			
		}
	}
}
