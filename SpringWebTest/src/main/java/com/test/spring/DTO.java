package com.test.spring;

import lombok.Data;

//Lombok > 아래의 어노테이션을 사용해서 getter, setter, toString을 자동으로 만들어 주는 기능
//@Data
//@Getter
//@Setter

@Data
public class DTO {
	
	private String seq;
	private String name;
	private String id;
	private String subject;
	private String content;
	private String regdate;

}
