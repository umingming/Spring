package com.test.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Ex03Controller {
	
	@RequestMapping(value="/ex03.do")
	public String call() {
		return "ex03";
	}
	
	@RequestMapping(value="/ex03ok.do", method={RequestMethod.POST})
	public String callok(
						HttpServletRequest req,
						DTO dto
//						String name, 
//						String age, 
//						String address
						) {
//		String name = req.getParameter("name");
//		String age = req.getParameter("age");
//		String address = req.getParameter("address");
//		
//		dto.setAge(age);
//		dto.setAddress(address);
		System.out.println(dto.toString());
		return "ex03ok";
	}

}
