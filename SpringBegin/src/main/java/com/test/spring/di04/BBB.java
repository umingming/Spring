package com.test.spring.di04;

public class BBB {
	private CCC c;
	
	public BBB(CCC c) {
		this.c = c;
	}
	
	public void run() {
		c.run();
	}
}
