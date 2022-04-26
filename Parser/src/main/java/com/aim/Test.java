package com.aim;

import java.io.File;

public class Test {
	public static void main(String[] args) {
		String path = "C:\\Aim\\abv.txt";
		File file = new File(path);
		System.out.println(file.getParent());
	}

}
