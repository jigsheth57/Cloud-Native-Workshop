package io.pivotal.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import io.pivotal.demo.domain.Contact;

@RestController
public class DataServiceController {

	private static final Logger logger = LoggerFactory.getLogger(DataServiceController.class);

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${contact.dataservice.endpoint:localhost:8080}")
	private String contactDataserviceEP;

	@Value("${contact.dataservice.unavailable}")
	private String unavailable;

	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
	public @ResponseBody String getAllContacts() {
		return restTemplate.getForObject(contactDataserviceEP+"/contacts", String.class);
	}

	@RequestMapping(value = "/contacts/search/findByFirstNameOrLastName/{name}", method = RequestMethod.GET)
	public @ResponseBody String searchContacts(@PathVariable("name") final String name) {
		logger.debug("{searchContacts} search existing contact("+name+")!");
		String url = contactDataserviceEP+"/contact";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		builder.queryParam("fname", name);
		builder.queryParam("lname", name);
		return restTemplate.getForObject(builder.build().encode().toUri(), String.class);
	}

	@RequestMapping(value = "/contacts", method = RequestMethod.POST)
	public @ResponseBody String createContact(@RequestBody final Contact contact) {
		String result = unavailable;
		result = restTemplate.postForObject(contactDataserviceEP+"/contact", contact,
				String.class);
		logger.debug("result: " + result);
		return result;
	}

	@RequestMapping(value = "/contacts/{id}", method = { RequestMethod.PUT })
	public @ResponseBody String updateContact(@PathVariable("id") final String contactId,
			@RequestBody final Contact contact) {
		String result = unavailable;
		logger.debug("{updateContact} Updating existing contact("+contactId+")!");
		logger.debug(contact.toString());
		restTemplate.put(contactDataserviceEP +"/contact/"+ contactId, contact);
		result = contact.toString();
		return result;
	}

	@RequestMapping(value = "/contacts/{id}", method={RequestMethod.GET, RequestMethod.DELETE})
	public @ResponseBody String rdContact(@PathVariable("id") final String contactId) {
	    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
		String method = request.getMethod().toLowerCase();
		String result = unavailable;
		switch (method) {
		case "delete":
			logger.debug("{rdContact} Deleting existing contact("+contactId+")!");
			restTemplate.delete(contactDataserviceEP+"/contact/"+contactId);
			result = "{\"status\":204,\"data\":\"Successful delete contact "+contactId+"\"}";
			break;
		default:
			logger.debug("{rdContact} Retrieving existing contact("+contactId+")!");
			result = restTemplate.getForObject(contactDataserviceEP+"/contact/"+contactId, String.class);
			break;
		}
		return result;
	}

	@RequestMapping(value = "/sessionid", method = RequestMethod.GET)
	public @ResponseBody String getSessionId() {
	    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
		return "{ \"sessionid\":\""+request.getSession().getId()+"\"}";
	}
}
