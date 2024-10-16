package com.choongang.shoppingmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.choongang.shoppingmall.service.TestService;

@Controller
@Configuration
public class TestController {
	@Autowired
	private TestService testService;
	
	@GetMapping("/test")
	public String test(Model model) {
		model.addAttribute("today", testService.getToday());
		return "test";
	}
	
}
