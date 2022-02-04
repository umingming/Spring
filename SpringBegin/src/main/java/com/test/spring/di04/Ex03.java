package com.test.spring.di04;

import com.test.spring.di04.AAA;
import com.test.spring.di04.BBB;
import com.test.spring.di04.CCC;

public class Ex03 {
	public static void main(String[] args) {
		DDD d = new DDD();
		CCC c = new CCC(d);
		BBB b = new BBB(c);
		AAA a = new AAA(b);
		
		a.run();
		
	}

}
