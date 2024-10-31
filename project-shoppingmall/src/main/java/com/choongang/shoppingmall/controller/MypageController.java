package com.choongang.shoppingmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.choongang.shoppingmall.service.QuestionService;
import com.choongang.shoppingmall.service.UserService;
import com.choongang.shoppingmall.service.WishService;
import com.choongang.shoppingmall.vo.QuestionVO;
import com.choongang.shoppingmall.vo.UserVO;
import com.choongang.shoppingmall.vo.WishVO;

@Controller
public class MypageController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private WishService wishService;
	@Autowired
	private QuestionService questionService;
	
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
	
    // 마이페이지
    @GetMapping("/myPage.html")
	public String myPage(Model model) {
    	boolean isLogin = isUserLoggedin();
		UserVO userVO = getUserInfo();
		if (!isLogin) 
			return "redirect:/login";
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("uservo", userVO);
    	
		return "myPage";
	}
	
	// 후기 관리
	@GetMapping("/my-reviewList.html")
	public String reviewList(Model model) {
		if(!isUserLoggedin())
			return "redirect:/login";
		UserVO userVO = getUserInfo();
		boolean isLogin = isUserLoggedin();
		model.addAttribute("uservo", userVO);
		model.addAttribute("isLogin", isLogin);
		
		return "/my-reviewList.html";
	}
	
	// 문의 내역
	@GetMapping("/my-question.html")
	public String questionList(Model model) {
		if(!isUserLoggedin())
			return "redirect:/login";
		UserVO userVO = getUserInfo();
		boolean isLogin = isUserLoggedin();
		List<QuestionVO> list = questionService.selectQuestionListByUserId(userVO.getUser_id());
		
		model.addAttribute("list", list);
		model.addAttribute("uservo", userVO);
		model.addAttribute("isLogin", isLogin);
		
		return "/my-question.html";
	}
}
