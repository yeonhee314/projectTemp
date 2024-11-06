package com.choongang.shoppingmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.choongang.shoppingmall.service.UserService;
import com.choongang.shoppingmall.vo.UserVO;

@Controller
public class OrderCompleteController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/orderComplete")
	public String showOrderCompletePage(@RequestParam int orderId, Model model) {
		// 유저 정보 가져오기
		UserVO userVO = getUserInfo();
		
		// 모델에 유저 정보 추가
		model.addAttribute("uservo", userVO);
		
		// orderComplete.html 을 반환
		return "orderComplete";
	}
	
	
	// 유저 정보 가져오는 메서드
	private UserVO getUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO userVO = null;
		
		if (authentication != null && authentication.isAuthenticated() &&
			!(authentication instanceof AnonymousAuthenticationToken)) {
			String username = authentication.getName();
			userVO = userService.selectByUsername(username); // 유저 정보 가져오기
		}
		return userVO;
	}
    
}
