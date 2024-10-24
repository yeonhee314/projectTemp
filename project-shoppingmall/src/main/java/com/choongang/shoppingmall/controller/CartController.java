package com.choongang.shoppingmall.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

		 @PostMapping("/cart/add")
		    public String addCart(@RequestParam("productId") int productId,@RequestParam("cartCount") int cartCount, 
		    		@RequestParam("productPrice") int productPrice, @RequestParam("productOption") String productOption, 
		    		HttpSession session, RedirectAttributes redirectAttributes) throws SQLException {
		    	
		        // 사용자 로그인 여부 확인
		        Integer userId = (Integer) session.getAttribute("userId");
		        System.out.println("세션 아이디:" +userId);

		        if (userId == null) {
		            redirectAttributes.addFlashAttribute("message", "로그인 후 장바구니에 상품을 추가할 수 있습니다.");
		            return "redirect:/login"; // 로그인 페이지로 리다이렉트
		        }
		        // 장바구니에 상품 추가
		        cartService.addCart(userId, productId, cartCount, productPrice, productOption);
		        redirectAttributes.addFlashAttribute("message", "상품이 장바구니에 추가되었습니다.");
		        return "redirect:/shoping-cart.html"; // 장바구니 페이지로 리다이렉트
		    }
	
			@GetMapping("/shoping-cart.html")
		    public String shopingCart(HttpSession session, Model model) {
		        Integer userId = (Integer) session.getAttribute("userId");
		       
		        List<CartVO> cartItems = cartService.getCartItems(userId);
		        
		        if (userId == null) {
		            return "redirect:/login"; // 로그인 안된 경우 로그인 페이지로 리다이렉트
		        }

		        // 사용자 ID로 장바구니 항목을 가져옴
		        model.addAttribute("cartItems", cartItems);
		        //log.info("로그"+cartItems);
		       
		        return "shoping-cart"; 

		 
		    }
	 
	}