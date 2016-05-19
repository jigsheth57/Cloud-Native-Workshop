package io.pivotal.demo.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.demo.domain.Contact;
import io.pivotal.demo.repository.ContactRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactController {

	@Autowired
	protected ContactRepository contactRepo;
	private static final Log log = LogFactory.getLog(ContactController.class);
	
	@RequestMapping(value="/contacts", method=RequestMethod.GET)
	@ApiOperation(value = "Retrieve all contacts",
    notes = "Calls contact repository to get all of the contacts",
    response = Contact.class,
    responseContainer = "List")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
	public @ResponseBody List<Contact> getAllContacts() {
		return contactRepo.findAll();
	}
	
	@RequestMapping(value = "/contact/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retrieve contact by id", response = Contact.class)
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
	public ResponseEntity<?> get(@ApiParam(value = "Contact ID", required = true) @PathVariable Long id) {
		Contact contact = null;
		HttpStatus httpstatus = HttpStatus.OK;
		if(contactRepo.exists(id)) {
			contact = contactRepo.findOne(id);
			log.debug(String.format("Found contact for id %d: [%s]",id, contact));
		} else {
			contact = new Contact();
			httpstatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<Contact>(contact, new HttpHeaders(), httpstatus);

	}
	
    @ApiOperation(value = "Retrieve contact by either firstname or lastname.", response = Contact.class, responseContainer = "List")
    @RequestMapping(method = RequestMethod.GET, path="/contact", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "fname", value = "Contact's firstname", required = false, dataType = "string", paramType = "query", defaultValue="Jig"),
        @ApiImplicitParam(name = "lname", value = "Contact's lastname", required = false, dataType = "string", paramType = "query", defaultValue="Sheth")
      })
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Contact.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
	public @ResponseBody List<Contact> getContactByName(@RequestParam(value="fname", required=false) String fname, @RequestParam(value="lname",required=false) String lname) {
		return contactRepo.findByFirstNameOrLastName(fname, lname);
	}

	@RequestMapping(value = "/contact/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete contact by id")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
	public ResponseEntity<?> delete(@ApiParam(value = "Contact ID", required = true) @PathVariable Long id) {
		HttpStatus httpstatus = HttpStatus.OK;
		if(contactRepo.exists(id)) {
			contactRepo.delete(id);
			log.debug(String.format("Remove contact for id %d",id));
		} else {
			httpstatus = HttpStatus.NOT_FOUND;
		}
		ResponseEntity.status(httpstatus);
		return ResponseEntity.noContent().build();

	}

	@RequestMapping(value = "/contact", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create new contact", response = Contact.class)
    @ApiResponses(value = { 
            @ApiResponse(code = 201, message = "Successfully created new contact."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Failure")}) 
	public ResponseEntity<?> post(@ApiParam(value = "Contact model", required = true) @RequestBody Contact contact) {
		contact = contactRepo.save(contact);
		HttpStatus httpstatus = HttpStatus.CREATED;
		log.debug(String.format("Created new contact with id %d: [%s]",contact.getId(), contact));
		return new ResponseEntity<Contact>(contact, new HttpHeaders(), httpstatus);

	}
	
	@RequestMapping(value = "/contact/{id}", method = RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update contact by id", response = Contact.class)
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
	public ResponseEntity<?> put(@ApiParam(value = "Contact ID", required = true) @PathVariable Long id, @ApiParam(value = "Contact model", required = true) @RequestBody Contact contact) {
		HttpStatus httpstatus = HttpStatus.OK;
		if(contactRepo.exists(id)) {
			contact.setId(id);
			contact = contactRepo.save(contact);
			log.debug(String.format("Updated contact for id %d: [%s]",id, contact));
		} else {
			httpstatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<Contact>(contact, new HttpHeaders(), httpstatus);

	}
}
