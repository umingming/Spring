package com.aim;

import java.io.File;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
	XML Parser
	- 파일 출력, 요소 CRUD, 다른 이름으로 저장
 */
@SpringBootApplication
public class ParserApplication {
	private static Parser parser;
	private static Scanner scan;
	private static Logger logger;
	
	private static String input;

	static {
		scan = new Scanner(System.in);
//		logger = LogManager.getLogger(ParserApplication.class);
	}
	
	/*
		main 메소드
		1. selectFile() 메소드 호출
		2. while문 무한루프
			> menu() 호출
			> switch문 input을 조건으로 사용
				> 1; print 호출
				> 2; setElement 호출
				> 3; addChild 호출
				> 4; toggle 호출
				> 5; rename 호출
	 */
	public static void main(String[] args) {
		SpringApplication.run(ParserApplication.class, args);
		try {
			parse();
			
			while(true) {
				menu();
				
				switch(input) {
					case "1" : 
						parser.print();
						break;
					case "2" :
						setElement();
						break;
					case "3" :
						addElement();
						break;
					case "4" :
						toggle();
						break;
					case "5" :
						rename();
						break;
					default :
						System.out.println("다시 입력해주세요.");
						break;
				}
			}
		} catch (Exception e) {
			System.out.println();
		}
	}

	private static void getTitle(String title) {
		System.out.printf("%n[%s]%n", title);
	}
	
	private static void enter() {
		System.out.print(" 👉 ");
		input = scan.nextLine();
	}
	
	private static void parse() throws Exception {
		getTitle("파일 경로 입력");
		
		while(true) {
			enter();
			File file = new File(input);
			
			if(file.exists()) {
				parser = new Parser(file);
				return;
			} else {
				System.out.println("다시 입력해주세요.");
			}
		}
	}

	private static void rename() {
	}

	private static void toggle() {
		// TODO Auto-generated method stub
		
	}

	private static void addElement() {
		// TODO Auto-generated method stub
		
	}

	private static void setElement() {
		// TODO Auto-generated method stub
		
	}

	private static void menu() {
		System.out.println("1. 파일 출력");
		System.out.println("2. 요소 수정");
		System.out.println("3. 요소 추가");
		System.out.println("4. 주석 처리");
		System.out.println("5. 다른 이름으로 저장");
		
		enter();
	}
}
