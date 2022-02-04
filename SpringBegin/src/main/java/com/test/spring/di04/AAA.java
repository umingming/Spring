package com.test.spring.di04;

public class AAA {
	private BBB b;
	
	public AAA(BBB b) {
		this.b = b;
	}
	
	public void run() {
		b.run();
	}
}
