package com.choongang.shoppingmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.choongang.shoppingmall.service.UsersBoardService;
import com.choongang.shoppingmall.vo.CommVO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.UserVO;

@Controller
@Configuration

public class AdminController {
	@Autowired
	private UsersBoardService usersBoardService;
	
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	@GetMapping(value =  "/admin/users")
	public String users(@ModelAttribute CommVO cv, Model model) {
		PagingVO<UserVO> pv = usersBoardService.getList(cv.getCurrentPage(), cv.getSizeOfPage(), cv.getSizeOfBlock());
		model.addAttribute("pv",pv);
		model.addAttribute("cv",cv);
		return "admin-users";
	}
	@GetMapping("/admin/user/details")
	public String adminUserDetails(@ModelAttribute CommVO cv, Model model) {
		PagingVO<UserVO> pv = usersBoardService.getList(cv.getCurrentPage(), cv.getSizeOfPage(), cv.getSizeOfBlock());
		model.addAttribute("pv",pv);
		model.addAttribute("cv",cv);
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
