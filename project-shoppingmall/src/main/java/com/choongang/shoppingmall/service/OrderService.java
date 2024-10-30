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
	OrdersVO getOrderById(int orderid, int user_id) throws SQLException;

	// 주문 ID에 대한 주문 완료 정보를 조회합니다.
	
	List<Order_CompleteVO> getOrderCompleteByOrderId(int orderId) throws SQLException;

	// 사용자 ID 에 따른 주문 목록 조회
	List<OrdersVO> getUserById(Integer userId) throws SQLException;

	
	
	
}
