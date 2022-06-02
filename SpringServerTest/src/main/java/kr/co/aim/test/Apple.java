package kr.co.aim.test;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Entity;

import lombok.Data;

//@Entity
@Data
public class Apple {
//	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int seq;
	
//	@Column(nullable = false, length = 30)
	private String name;
}
