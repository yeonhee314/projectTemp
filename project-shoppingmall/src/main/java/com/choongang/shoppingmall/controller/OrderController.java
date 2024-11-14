package com.choongang.shoppingmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choongang.shoppingmall.service.OrderService;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/addToOrder")
	public void addToOrder(@RequestParam("user_id") int user_id) {
		
	}
}
