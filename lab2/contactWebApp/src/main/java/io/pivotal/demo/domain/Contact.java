package io.pivotal.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Contact {

	@JsonProperty(value="Id")
	private Long Id;
	
	@JsonProperty(value="firstName")
	private String firstName;
	
	@JsonProperty(value="lastName")
	private String lastName;
	
	@JsonProperty(value="title")
	private String title;
	
	@JsonProperty(value="email")
	private String email;
	
	@JsonProperty(value="phone")
	private Phone phone;
	
	public Contact() {
		super();
	}
	
	public Contact (String title, String firstName, String lastName, String email, Phone phone) {
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return String.format(
				"{\"Id\": %d, \"title\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\", \"phone\": %s}",
				Id, 
				title, 
				firstName,
				lastName,
				email,
				phone);
	}
	
	

}