package com.choongang.shoppingmall.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choongang.shoppingmall.service.AddressService;
import com.choongang.shoppingmall.service.QuestionService;
import com.choongang.shoppingmall.service.UserService;
import com.choongang.shoppingmall.service.WishService;
import com.choongang.shoppingmall.vo.AddressVO;
import com.choongang.shoppingmall.vo.QuestionVO;
import com.choongang.shoppingmall.vo.UserVO;
import com.choongang.shoppingmall.vo.WishVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class MypageController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private WishService wishService;
	@Autowired
	private QuestionService questionService;
	@Autowired 
	private AddressService addressService;
	
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

					@GetMapping("/my-addrList.html")
					public String addressList(HttpSession session,Model model) throws SQLException {

						int userId = (int) session.getAttribute("userId");
						
						List<AddressVO> addressList = addressService.getAddressList(userId);
		
						UserVO user = userService.getUserById(userId);

						if(!isUserLoggedin())
							return "redirect:/login";
						UserVO userVO = getUserInfo();
						boolean isLogin = isUserLoggedin();
						
						model.addAttribute("uservo", userVO);
						model.addAttribute("isLogin", isLogin);
						model.addAttribute("user", user);
						model.addAttribute("addressVO", new AddressVO());
						model.addAttribute("addressList",addressList);
						
						return "/my-addrList.html";
					}
										
										
	// 회원 정보 확인
		@GetMapping("/my-modify.html")
		public String getProfile(HttpSession session,Model model) throws SQLException {
			if(!isUserLoggedin())
				return "redirect:/login";
			UserVO userVO = getUserInfo();
			boolean isLogin = isUserLoggedin();
			
			int userId=(int) session.getAttribute("userId");
			UserVO user = userService.getUserById(userId);
			
			//model.addAttribute("list", list);
			model.addAttribute("uservo", userVO);
			model.addAttribute("isLogin", isLogin);
			model.addAttribute("user", user);
			
			return "/my-modify.html";
		}
		
	//회원 정보 수정
		@PostMapping("/updateProfile")
		public String updateProfile(UserVO userVO,HttpSession session) throws SQLException {
			int userId=(int) session.getAttribute("userId");
			userVO.setUser_id(userId);
			userService.updateUser(userVO);
			
			
			return "redirect:/my-modify.html";
			
		}
		
}
