package com.choongang.shoppingmall.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.choongang.shoppingmall.vo.Order_DetailVO;
import com.choongang.shoppingmall.vo.OrdersVO;

@Mapper
@Repository
public interface OrderDAO {
	// 주문서 작성
	void insertOrder(OrdersVO ordersVO) throws SQLException;
	
	// 주문서 상세 삽입
	void insertOrderDetailVO(Order_DetailVO order_DetailVO) throws SQLException;

	// 주문 조회
	OrdersVO selectOrderById(int orderId) throws SQLException;
	
}
