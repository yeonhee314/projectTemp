package com.choongang.shoppingmall.dao;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.MyPageReviewInfo;
import com.choongang.shoppingmall.vo.Order_ItemVO;
import com.choongang.shoppingmall.vo.OrdersVO;

@Mapper
public interface OrderDAO {
	// 주문 개수
	int selectOrderCount(HashMap<String, String> map) throws SQLException;
	// 한 페이지 얻기
	List<OrdersVO> selectAdminOrderList(HashMap<String, String> map) throws SQLException;
	
	// 마이페이지 리뷰관리 : 아이디별 주문 내역
	List<MyPageReviewInfo> selectByMyReview(int user_id) throws SQLException;
	// 마이페이지 리뷰관리 : 아이디별 주문 내역 갯수
	int selectByMyReviewCount(int user_id) throws SQLException;
	
	// 주문 테이블 저장
	void addToOrder(OrdersVO vo) throws SQLException;
	// 주문 상품 리스트 저장
	void addToOrderItems(Order_ItemVO vo) throws SQLException;
	// 가장 큰 주문번호 가져오기
	Integer selectMaxOrderId() throws SQLException;
	// 첫번째 주문 데이터 가져올때만 사용한다.
	Integer selectFirstOrdersId() throws SQLException;
}
