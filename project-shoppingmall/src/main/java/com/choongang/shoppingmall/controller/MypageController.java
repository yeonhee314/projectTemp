package com.choongang.shoppingmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.choongang.shoppingmall.service.UserService;
import com.choongang.shoppingmall.service.WishService;
import com.choongang.shoppingmall.vo.UserVO;
import com.choongang.shoppingmall.vo.WishVO;

@Controller
public class MypageController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private WishService wishService;
	
	// 로그인 여부 확인
	public boolean isUserLoggedin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
	}
	
	// 회원 정보 가져오기
	public UserVO getUserInfo() {
		UserVO userVO = new UserVO();
		List<WishVO> wishList = null;
		if(isUserLoggedin()) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			userVO = userService.selectByUsername(username);
			wishList = wishService.selectWishByUserId(userVO.getUser_id());
			userVO.setWishList(wishList);
		}
		return userVO;
	}
	
	@GetMapping("/my-reviewList.html")
	public String reviewList(Model model) {
		if(!isUserLoggedin())
			return "redirect:/login";
		UserVO userVO = getUserInfo();
		model.addAttribute("uservo", userVO);
		
		return "/my-reviewList.html";
	}
}
