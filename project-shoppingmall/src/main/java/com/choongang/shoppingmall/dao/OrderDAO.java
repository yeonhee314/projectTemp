package com.choongang.shoppingmall.dao;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.Order_ItemVO;
import com.choongang.shoppingmall.vo.OrdersVO;

@Mapper
public interface OrderDAO {
	// 주문 개수
	int selectOrderCount(HashMap<String, String> map) throws SQLException;
	// 한 페이지 얻기
	List<OrdersVO> selectAdminOrderList(HashMap<String, String> map) throws SQLException;
	// 주문 테이블 저장
	void addToOrder(OrdersVO vo) throws SQLException;
	// 주문 상품 리스트 저장
	void addToOrderItems(Order_ItemVO vo) throws SQLException;
}
