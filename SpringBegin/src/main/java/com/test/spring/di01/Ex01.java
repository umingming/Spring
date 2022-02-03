package com.test.spring.di01;

public class Ex01 {
	public static void main(String[] args) {
		Hong hong = new Hong();
		hong.run();
		
		Pen pen = new Pen();
		
		Lee lee = new Lee(pen);
		lee.run();
	}
}
