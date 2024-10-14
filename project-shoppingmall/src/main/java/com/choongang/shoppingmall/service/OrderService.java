package com.choongang.shoppingmall.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.vo.Order_CompleteVO;
import com.choongang.shoppingmall.vo.OrdersVO;

@Service
public interface OrderService {

	// 주문서 작성
	void createOrder(OrdersVO ordersVO) throws SQLException;
	
	// 주문 상세 작성
	void createOrder_Complete(Order_CompleteVO orderComplete) throws SQLException;
	
	// 주문 조회
	OrdersVO getOrderByid(int orderid) throws SQLException;

	List<Order_CompleteVO> getOrderCompleteByOrderId(int orderId);



	
	
}
