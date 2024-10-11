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

	// 관리자 페이지 접근
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	// 전체 회원 목록
	@GetMapping(value =  "/admin/users")
	public String users(Model model) {
		model.addAttribute("list",usersBoardService.selectAll());
		model.addAttribute("count",usersBoardService.selectCount());
		return "admin-users";
	}
	
	// 회원 상세 조회
	@GetMapping(value = "/admin/user/details")
	public String adminUserDetail(Integer user_id, Model model) {
		model.addAttribute("user_id",user_id);
		model.addAttribute("details", usersBoardService.selectByID(user_id));
		return "admin-userdetails";
	}
	// 상품 관리
	@GetMapping("/admin/products")
	public String adminProducts() {
		return "admin-products";
	}
	// 문의 관리
	@GetMapping("/admin/qna")
	public String adminQna() {
		return "admin-qna";
	}
}
