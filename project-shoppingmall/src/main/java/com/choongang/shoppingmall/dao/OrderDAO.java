package com.choongang.shoppingmall.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.choongang.shoppingmall.vo.Order_CompleteVO;
import com.choongang.shoppingmall.vo.OrdersVO;

@Mapper
@Repository
public interface OrderDAO {
	// 주문서 작성
	void insertOrder(OrdersVO ordersVO) throws SQLException;
	
	// 주문 완료 페이지
	void insertOrderCompleteVO(Order_CompleteVO order_CompleteVO) throws SQLException;

	// 주문 조회
	OrdersVO selectOrderById(int orderId) throws SQLException;

	// 사용자 ID에 따른 주문 목록 조회
	List<OrdersVO> selectOrdersByUserId(Integer user_id) throws SQLException;

	// 주문 ID에 대한 주문 완료 정보를 조회합니다.
	List<Order_CompleteVO> selectOrderCompleteByOrderId(int orderId) throws SQLException;
	
}
