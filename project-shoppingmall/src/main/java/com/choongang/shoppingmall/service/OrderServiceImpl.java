package com.choongang.shoppingmall.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.OrderDAO;
import com.choongang.shoppingmall.vo.Order_CompleteVO;
import com.choongang.shoppingmall.vo.OrdersVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderDAO orderDAO;

	// 주문서 작성
	@Override
	public void createOrder(OrdersVO ordersVO)  {
		log.info("주문서 작성 시작 : {} ", ordersVO);
		try {
			orderDAO.insertOrder(ordersVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("주문서 작성 완료 : {} ", ordersVO);
		
		
	}
	// 주문서 작성
	@Override
	public void createOrder_Complete(Order_CompleteVO order_CompleteVO)  {
		log.info("주문서 작성 시작 : {} ", order_CompleteVO);
		try {
			orderDAO.insertOrderCompleteVO(order_CompleteVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("주문서 작성 완료 : {} ", order_CompleteVO);
		
		
	}
	

	// 주문 조회
	@Override
	public OrdersVO getOrderById(int orderid, int user_id) {
		log.info("주문서 조회 시작 : {} ", orderid);
		OrdersVO ordersVO = null;
		try {
			ordersVO = orderDAO.selectOrderById(orderid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("주문서 조회 완료 : {} ", ordersVO);
		return ordersVO;
	}
	@Override
	public List<Order_CompleteVO> getOrderCompleteByOrderId(int orderId) {
	    log.info("주문 완료 조회 시작: orderId = {}", orderId);
	    List<Order_CompleteVO> orderCompleteList = null;
		try {
			orderCompleteList = orderDAO.selectOrderCompleteByOrderId(orderId);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	    log.info("주문 완료 조회 완료: {}", orderCompleteList);
	    return orderCompleteList;
	}

	@Override
	public List<OrdersVO> getOrdersByUserId(Integer user_id) {
	    log.info("사용자 ID에 따른 주문 목록 조회 시작 : user_id = {}", user_id);
	    List<OrdersVO> ordersList = null;
		try {
			ordersList = orderDAO.selectOrdersByUserId(user_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    log.info("사용자 ID에 따른 주문 목록 조회 완료 : {} ", ordersList);
	    return ordersList;
	}
	
	
}
