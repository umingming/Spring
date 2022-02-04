package com.test.spring.di02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Hong {
	private Pen pen;
	private Hong(Pen pen) {
		this.pen = pen;
	}
	private Hong() {
		this.pen = null;
	}
	
	public void setPen(Pen pen) {
		ApplicationContext context = new ClassPathXmlApplicationContext(".\\di02.xml");
		pen = (Pen)context.getBean("pen");
	}
	public void run() {
		pen.draw();
	}
}
