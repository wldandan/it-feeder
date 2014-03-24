package com.it.epolice.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class ReceiveFileController {
 
	@RequestMapping("/it/file/1.jpg")
//	@RequestMapping(method = RequestMethod.PUT)
	ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
		System.out.println(request);
		return new ModelAndView();
	}
	
//	@RequestMapping(method = RequestMethod.PUT)
//	public String receive(ModelMap model) {
// 
//		model.addAttribute("message", "Spring 3 MVC Hello World");
//
//		return "hello";
//	}
 
}