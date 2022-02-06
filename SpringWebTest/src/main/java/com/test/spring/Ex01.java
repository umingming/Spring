package com.test.spring;

public class Ex01 {
	
	public static void main(String[] args) {
		
		DTO dto = new DTO();
		
		dto.setSeq("hong");
		dto.setName("홍길동");
		
		System.out.println(dto.getName());
		
		System.out.println(dto.toString());
		
	}

}
