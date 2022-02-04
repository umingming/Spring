package com.test.spring.di04;

public class CCC {
	private DDD d;
	
	public CCC(DDD d) {
		this.d = d;
	}
	
	public void run() {
		d.run();
	}

}
