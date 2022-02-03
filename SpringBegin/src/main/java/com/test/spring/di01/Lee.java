package com.test.spring.di01;

public class Lee {
	private Pen pen;
	
	public Lee(Pen pen) {
		this.pen = pen;
	}
	
	public void run() {
		pen.draw();
	}
}
