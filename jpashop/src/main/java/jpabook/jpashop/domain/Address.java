package jpabook.jpashop.domain;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter 
public class Address {
	private String city;
	private String street;
	private String zipcode;
	
	protected Address() {
		
	}
	
	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
}
