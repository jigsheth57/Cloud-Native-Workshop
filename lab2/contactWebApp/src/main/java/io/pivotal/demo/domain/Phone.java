package io.pivotal.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Phone {

	@JsonProperty(value="type")
	PhoneType type;
	
	@JsonProperty(value="value")
	String value;
	
	public Phone() {}
	
	public Phone(PhoneType type, String value) {
		this.type = type;
		this.value = value;
	}
	public PhoneType getType() {
		return type;
	}
	public void setType(PhoneType type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return String.format(
				"{\"type\": \"%s\", \"value\": \"%s\"}",
				type, 
				value);
	}
}
