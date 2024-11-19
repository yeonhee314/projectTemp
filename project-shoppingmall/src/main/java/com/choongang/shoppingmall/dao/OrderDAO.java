package com.choongang.shoppingmall.dao;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.Order_ItemVO;
import com.choongang.shoppingmall.vo.OrdersVO;

@Mapper
public interface OrderDAO {
	// 주문 테이블 저장
	void addToOrder(OrdersVO vo) throws SQLException;
	// 주문 상품 리스트 저장
	void addToOrderItems(Order_ItemVO vo) throws SQLException;
	// 가장 큰 주문번호 가져오기
	int selectMaxOrderId() throws SQLException;
}
