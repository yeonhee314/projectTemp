package com.choongang.shoppingmall.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choongang.shoppingmall.service.OrderService;
import com.choongang.shoppingmall.vo.OrdersVO;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/addToOrder")
	@ResponseBody
	public void addToOrder(@RequestBody Map<String, String> request) {
		String user_id = request.get("user_id");
		String merchant_uid = request.get("merchant_uid");
		String price = request.get("total_price");
		String paymentType = request.get("payment_type");
		String status = request.get("status");
		String address = request.get("address");
		String request_type = request.get("request_type");
		
		OrdersVO vo = new OrdersVO();
		vo.setUser_id(Integer.parseInt(user_id));
		vo.setMerchant_uid(merchant_uid);
		vo.setTotal_price(Integer.parseInt(price));
		vo.setPayment_type(paymentType);
		vo.setStatus(status);
		vo.setAddress(address);
		vo.setRequest_type(request_type);
		
		orderService.addToOrder(vo);
		// TODO : order id가 가장 큰 값 가져오기
	}
}



