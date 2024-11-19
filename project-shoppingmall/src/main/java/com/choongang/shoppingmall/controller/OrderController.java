package com.choongang.shoppingmall.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choongang.shoppingmall.service.OrderService;
import com.choongang.shoppingmall.vo.Order_ItemVO;
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
	}
	
	@PostMapping("/addToOrderItem")
	@ResponseBody
	public void addToOrderItem(@RequestBody List<Map<String, Object>> ordersItem) {
		for(Map<String, Object> item : ordersItem) {
			Order_ItemVO vo = new Order_ItemVO();
			vo.setOrder_id(orderService.selectMaxOrderId());
			vo.setUser_id((Integer)item.get("user_id"));
			vo.setProduct_id((Integer)item.get("product_id"));
			vo.setQuantity((Integer)item.get("quantity"));
			vo.setOrder_price((Integer)item.get("price"));
			orderService.addToOrderItems(vo);
		}
	}
}



