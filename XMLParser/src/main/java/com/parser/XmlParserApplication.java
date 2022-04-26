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
	private static Logger logger;
	private static Scanner scan;
	
	static {
		logger = LogManager.getLogger(XmlParserApplication.class);
		scan = new Scanner(System.in);
	}
	
	public static void main(String[] args) {
		try {
			SpringApplication.run(XmlParserApplication.class, args);
			
			/* 파싱 */
			parse();

			/* 출력 */
			parser.print();
			
			/* 요소 수정 */
			modify();
			
			/* 요소 추가 */
			addChild();
			
			/* 요소 삭제 */
			parser.remove(getTag());
			
			/* 주석 처리 */
			parser.toggleComment(getTag());
			
			/* 다른 이름으로 저장 */
			rename();
			
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private static void rename() throws Exception {
		System.out.println("Enter a new name. \r\n 👉 ");
		parser.rename(scan.nextLine());
	}

	private static void modify() {
		Element element = parser.navigate(getTag());
		
		if(element != null) {
			System.out.println("Enter a attribute to be modified. \r\n 👉 ");
			String attr = scan.nextLine();
			System.out.println("Enter a Value for the attribute. \r\n 👉 ");
			String value = scan.nextLine();
			
			parser.modify(element, attr, value);
		}
	}

	private static Tag getTag() {
		Tag tag= new Tag();
		
		System.out.println("Enter a tag type. \r\n 👉 ");
		tag.setTag(scan.nextLine());
		
		System.out.println("Enter the tag name. \r\n 👉 ");
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
		System.out.println("Enter a file to parse. \r\n 👉 ");
		path = scan.nextLine();
	}
}