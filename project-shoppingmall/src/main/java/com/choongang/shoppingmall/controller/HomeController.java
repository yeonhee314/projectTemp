package com.choongang.shoppingmall.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Configuration
public class HomeController {
	
	@GetMapping("/index.html")
	public String index() {
		return "index";
	}
	@GetMapping("/about.html")
	public String about() {
		return "about";
	}
	@GetMapping("/blog.html")
	public String blog() {
		return "blog";
	}
	@GetMapping("/blog-detail.html")
	public String blogDetail() {
		return "blog-detail";
	}
	@GetMapping("/contact.html")
	public String contact() {
		return "contact";
	}
	@GetMapping("/home-02.html")
	public String home02() {
		return "home-02";
	}
	@GetMapping("/home-03.html")
	public String home03() {
		return "home-03";
	}
	@GetMapping("/product.html")
	public String product() {
		return "product";
	}
	@GetMapping("/product-detail.html")
	public String productDetail() {
		return "product-detail";
	}
	@GetMapping("/shoping-cart.html")
	public String shopingCart() {
		return "shoping-cart";
	}
}
