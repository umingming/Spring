package com.test.spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
//		IMemo memo = new Memo();
		
		ApplicationContext context = new ClassPathXmlApplicationContext("memo.xml");
		IMemo memo = (IMemo)context.getBean("memo");;
		
		memo.auth_add("메모 테스트입니다.");
//		
//		try {
//			memo.read(5);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		memo.auth_edit(5, "memo testing..");
//		
//		memo.del(5);
		
		
	}
}
