package com.test.spring.di05;

public class BBB {
	private CCC c;
	
	public BBB(CCC c) {
		this.c = c;
	}
	
	public void run() {
		c.run();
	}
}
