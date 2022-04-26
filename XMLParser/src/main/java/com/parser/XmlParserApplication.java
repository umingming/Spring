package com.parser;

import java.io.File;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.parser.jdom.JdomParser;
import com.parser.jdom.Tag;

@SpringBootApplication
public class XmlParserApplication {
	private static String path;
	
	private static JdomParser parser;
	private static Scanner scan;
	
	static {
		scan = new Scanner(System.in);
	}
	
	public static void main(String[] args) {
		try {
			SpringApplication.run(XmlParserApplication.class, args);
			
			parse();
			
			while(true) {
				menu();
				
				switch(scan.nextLine()) {
					case "1" : 
						parser.print();
						break;
					case "2" :
						modify();
						break;
					case "3" :
						addChild();
						break;
					case "4" :
						parser.commentOut(getTag());
						break;
					case "5" :
						parser.remove(getTag());
						break;
					case "6" :
						rename();
						break;
					default :
						System.out.println("Please Enter again.");
						break;
				}
				pause();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	private static void rename() throws Exception {
		System.out.print("Enter a new name. \r\n 👉 ");
		parser.rename(scan.nextLine());
	}

	private static void modify() {
		Element element = parser.navigate(getTag());
		
		if(element != null) {
			System.out.print("Enter a attribute to be modified. \r\n 👉 ");
			String attr = scan.nextLine();
			System.out.print("Enter a Value for the attribute. \r\n 👉 ");
			String value = scan.nextLine();
			
			parser.modify(element, attr, value);
		}
	}

	private static Tag getTag() {
		Tag tag= new Tag();
		
		System.out.print("Enter a tag type. \r\n 👉 ");
		tag.setTag(scan.nextLine());
		
		System.out.print("Enter the tag name. \r\n 👉 ");
		tag.setName(scan.nextLine());
		
		return tag;
	}
	
	private static void parse() throws Exception {
		setPath();
		
		if(new File(path).exists()) {
			parser = new JdomParser(path); 
		}
	}

	private static void addChild() {
		Element element = parser.navigate(getTag());
		
		if(element != null) {
			parser.createChild(element, getTag());
		}
	}

	public static void setPath() {
		System.out.print("Enter a file to parse. \r\n 👉 ");
		path = scan.nextLine();
	}
	
	private static void pause() {
		System.out.print("\r\n(Please press enter.)");
		scan.nextLine();
	}
	
	private static void menu() {
		System.out.println("\r\n1. Print the file");
		System.out.println("2. Modify an element");
		System.out.println("3. Add an element");
		System.out.println("4. Comment out an element");
		System.out.println("5. Remove an element");
		System.out.print("6. Rename the file\r\n 👉 ");
	}
}