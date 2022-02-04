package com.test.spring.di05;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Ex03 {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("di03.xml");
		AAA a = (AAA)context.getBean("a");
		a.run();
		
	}

}
