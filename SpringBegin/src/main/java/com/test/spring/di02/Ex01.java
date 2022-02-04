package com.test.spring.di02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Ex01 {
	public static void main(String[] args) {
//		
//		ApplicationContext context = new ClassPathXmlApplicationContext(".\\di02.xml");
//		Pen pen = (Pen)context.getBean("pen");
//		pen.draw();
//		
//		Hong hong = (Hong)context.getBean("hong");
//		hong.setPen(pen);
//		hong.run();
		
		ApplicationContext context = new ClassPathXmlApplicationContext("di02.xml");
		Hong hong = (Hong)context.getBean("hong");
		hong.run();
		
	}
}
