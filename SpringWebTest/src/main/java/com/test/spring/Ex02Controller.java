package com.test.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//JSP Model2의 서블릿(컨트롤러) 역할
@Controller
public class Ex02Controller {
	
	//private DAO dao = new DAO();
	
	//doGet() or doPost()의 역할 > 요청 메소드
	//http://localhost:8090/spring/ex02.do
	@RequestMapping(value="/ex02.do")
	public String test() {
		
		//DAO dao = new DAO();
		
		//페이지 제작 작업
		System.out.println("ex02");
		
		//JSP 페이지 호출하기
		return "ex02";
		//return "/WEB-INF/views/ex02.jsp";
	}
	
	@RequestMapping(value="/ex02_1.do")
	public String test2() {
		
		//DAO dao = new DAO();
		
		return "ex02_1";
	}
	
	@RequestMapping(value="/ex02_2.do")
	public String test3() {
		
		//"/WEB-INF/views/"
		//".jsp"
		
		///WEB-INF/views/ex02_2.jsp
		return "member/ex02_2";
	}
	
	public void aaa() {
		
	}
	
}










