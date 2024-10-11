package com.choongang.shoppingmall.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.OrderDAO;
import com.choongang.shoppingmall.vo.Order_DetailVO;
import com.choongang.shoppingmall.vo.OrdersVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderDAO orderDAO;

	// 주문서 작성
	@Override
	public void createOrder(OrdersVO ordersVO) throws SQLException {
		log.info("주문서 작성 시작 : {} ", ordersVO);
		orderDAO.insertOrder(ordersVO);
		log.info("주문서 작성 완료 : {} ", ordersVO);
		
		
	}
	// 주문서 작성
	@Override
	public void createOrder_Detail(Order_DetailVO order_DetailVO) throws SQLException {
		log.info("주문서 작성 시작 : {} ", order_DetailVO);
		orderDAO.insertOrderDetailVO(order_DetailVO);
		log.info("주문서 작성 완료 : {} ", order_DetailVO);
		
		
	}
	

	// 주문 조회
	@Override
	public OrdersVO getOrderByid(int orderid) throws SQLException {
		log.info("주문서 조회 시작 : {} ", orderid);
		OrdersVO ordersVO = orderDAO.selectOrderById(orderid);
		log.info("주문서 조회 완료 : {} ", ordersVO);
		return ordersVO;
	}
	@Override
	public List<Order_DetailVO> getOrderDetailsByOrderId(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
