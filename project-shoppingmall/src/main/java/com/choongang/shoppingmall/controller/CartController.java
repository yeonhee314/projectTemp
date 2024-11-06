package com.choongang.shoppingmall.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choongang.shoppingmall.service.CartService;
import com.choongang.shoppingmall.vo.CartVO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CartController {

	@Autowired
	private CartService cartService;

	// 장바구니 추가
	@PostMapping("/cart/add")
	public String addCart(@RequestParam("productId") int productId, 
						  @RequestParam("cartCount") int cartCount,
						  @RequestParam("productPrice") int productPrice, 
						  @RequestParam("productOption") String productOption,
						  @RequestParam("categoryId") int categoryId,
						  @RequestParam("isResume") boolean isResume,
						  HttpSession session, 
						  RedirectAttributes redirectAttributes) throws SQLException {

		// 사용자 로그인 여부 확인
		Integer userId = (Integer) session.getAttribute("userId");
		System.out.println("세션 아이디:" + userId);

		if (userId == null) {
			redirectAttributes.addFlashAttribute("message", "로그인 후 장바구니에 상품을 추가할 수 있습니다.");
			return "redirect:/login"; // 로그인 페이지로 리다이렉트
		}
		// 장바구니에 상품 추가
		cartService.addCart(userId, productId, cartCount, productPrice, productOption);
		redirectAttributes.addFlashAttribute("message", "상품이 장바구니에 추가되었습니다.");
		
		if(isResume == false) {
			return "redirect:/shoping-cart.html";
		}else {
			return "redirect:/product-detail.html?product_id=" + productId + "&category_id=" + categoryId;
		}
	}
	
	// 위시리스트 장바구니 - Ajax 사용
	@PostMapping("/cart/addAjax")
	public ResponseEntity<Map<String, String>> addCartAjax(@RequestParam("productId") int productId, 
						  @RequestParam("cartCount") int cartCount,
						  @RequestParam("productPrice") int productPrice, 
						  @RequestParam("productOption") String productOption,
						  @RequestParam("categoryId") int categoryId,
						  @RequestParam("isResume") boolean isResume,
						  HttpSession session, 
						  RedirectAttributes redirectAttributes) throws SQLException {

		// 로그인 상태여야 위시리스트에 진입 가능하므로 유효성검사는 하지 않음
		Integer userId = (Integer) session.getAttribute("userId");
		
		// 장바구니에 상품 추가
		cartService.addCart(userId, productId, cartCount, productPrice, productOption);
		
		 Map<String, String> response = new HashMap<>();
		 response.put("redirect", isResume ? "/wishlist.html" : "/shoping-cart.html");

		return ResponseEntity.ok(response);
	}

	// 장바구니 삭제
	@GetMapping("/cart/delete")
	public String deleteCart(@RequestParam("cartId") int cartId) {
		cartService.deleteCart(cartId);
		return "redirect:/shoping-cart.html";
	}

	// 장바구니 수량 수정
	@PostMapping("/cart/update")
	public String updateCart(CartVO cartVO) {

		cartService.updateCart(cartVO);

		// log.info("로그"+cartVO);

		return "redirect:/shoping-cart.html";
	}

}