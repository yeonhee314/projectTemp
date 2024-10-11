package com.choongang.shoppingmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.choongang.shoppingmall.service.UsersBoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Configuration
@Slf4j
public class AdminController {
	@Autowired
	private UsersBoardService usersBoardService;

	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

	@GetMapping(value =  "/admin/users")
	public String users(Model model) {
		model.addAttribute("list",usersBoardService.selectAll());
		model.addAttribute("count",usersBoardService.selectCount());
		return "admin-users";
	}
	
	// 회원 상세 조회
	@GetMapping("/admin/user/details")
	public String adminUserDetails(int user_id, Model model) {
		
		return "admin-userdetails";
	}
	
	@GetMapping("/admin/products")
	public String adminProducts() {
		return "admin-products";
	}
	@GetMapping("/admin/qna")
	public String adminQna() {
		return "admin-qna";
	}
}
