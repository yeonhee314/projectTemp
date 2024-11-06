package com.choongang.shoppingmall.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.choongang.shoppingmall.service.OrderService;
import com.choongang.shoppingmall.service.UserService; // UserService 주입
import com.choongang.shoppingmall.vo.OrdersVO;
import com.choongang.shoppingmall.vo.UserVO;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService; // OrderService 주입
	@Autowired
	private UserService userService; // UserService 주입

	// 주문 생성 페이지
	@GetMapping("/createOrder")
	public String createOrder(Model model) {
		// 유저 정보 가져오기
		UserVO userVO = getUserInfo();

		// 유저가 로그인했는지 확인
		if (userVO == null || userVO.getUser_id() == 0) {
			return "login"; // 로그인 페이지로 이동
		}

		// 모델에 유저 정보 추가
		model.addAttribute("uservo", userVO);

		return "createOrder"; // 주문 생성 페이지로 이동
	}

	// 주문 생성 처리
	@PostMapping("/createOrder")
	public String processOrder(OrdersVO orderVO, Model model) throws SQLException {
		// 주문 생성 로직
		orderService.createOrder(orderVO);

		// 주문 완료 페이지로 리다이렉트
		return "orderComplete";
	}

	// 유저 정보 가져오는 메서드
	private UserVO getUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO userVO = null;

		if (authentication != null && authentication.isAuthenticated()
				&& !(authentication instanceof AnonymousAuthenticationToken)) {
			String username = authentication.getName();
			userVO = userService.selectByUsername(username); // 유저 정보 가져오기
		}
		return userVO;
	}

	// 전화번호를 '000-0000-0000' 형식으로 변환하는 메서드
	public static String formatPhoneNumber(String phone) {
		if (phone != null && phone.length() == 11) {
			return phone.replaceFirst("(\\d{3})(\\d{4})(\\d+)", "$1-$2-$3");
		}
		return phone;
	}
}
