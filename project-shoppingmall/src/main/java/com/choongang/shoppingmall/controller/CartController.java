package com.choongang.shoppingmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choongang.shoppingmall.service.CartService;

import jakarta.servlet.http.HttpSession;



@Controller
public class CartController {

	  @Autowired
	    private CartService cartService;

	    @PostMapping("/cart/add")

	    public String addToCart(@RequestParam("productId") int productId,@RequestParam("cartCount") int cartCount, 
	    		@RequestParam("productPrice") int productPrice, @RequestParam("productOption") String productOption, 
	    		HttpSession session, RedirectAttributes redirectAttributes) {
	    	
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

	    @GetMapping("/cart")
	    public String viewCart(HttpSession session, Model model) {
	        Integer userId = (Integer) session.getAttribute("userId");
	        if (userId != null) {
	            model.addAttribute("cartItems", cartService.getCartByUserId(userId));
	        }
	        return "cart"; // cart.html 페이지로 이동
	    }

	    @PostMapping("/cart/delete")
	    public String deleteCartItem(@RequestParam int cartId) {
	        cartService.deleteCartItem(cartId);
	        return "redirect:/cart"; // 장바구니 페이지로 리다이렉트
	    }
	 
	}