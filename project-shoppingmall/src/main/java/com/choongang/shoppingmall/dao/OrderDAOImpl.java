package com.choongang.shoppingmall.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.choongang.shoppingmall.vo.Order_CompleteVO;
import com.choongang.shoppingmall.vo.OrdersVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class OrderDAOImpl implements OrderDAO {

	@Autowired
	private SqlSession sqlSession;

	// 주문서 작성 / 배송지 / 요청사항
	@Override
	public void insertOrder(OrdersVO ordersVO) throws SQLException {
		log.info("zipcode: {}," // 우편번호
				+ " addr: {}," // 주소
				+ " addr_detail: {}," // 상세 주소
				+ " req: {}", // 요청 사항
				ordersVO.getZipcode(),
				ordersVO.getAddr(),
				ordersVO.getAddr_detail(),
				ordersVO.getReq());
		sqlSession.insert("com.choongang.shoppingmall.dao.OrdersDAO.insertOrder", ordersVO);

	}

	// 주문 상세 작성 / 주문 상품 / 할인율 /결제수단 / 총 결제 금액
	@Override
	public void insertOrderCompleteVO(Order_CompleteVO order_CompleteVO) throws SQLException {
		log.info("product_name: {}," // 상품 이름
				+ " quantity: {}," // 상품 수량
				+ " product_price: {}," // 상품 가격
				+ " thumb: {}," // 상품 썸네일
				+ " product_status: {}," // 상품 옵션
				+ " discount: {}," // 할인율
				+ " pay_method: {}," // 결제 수단
				+ " total_price: {},", // 총 결제 금액
				order_CompleteVO.getProduct_name(), 
				order_CompleteVO.getProduct_price(),
				order_CompleteVO.getThumb(),
				order_CompleteVO.getProduct_status(),
				order_CompleteVO.getDiscount(),
				order_CompleteVO.getPay_ment(),
				order_CompleteVO.getTotal_price());
		sqlSession.insert("com.choongang.shoppingmall.dao.OrdersDAO.insertOrder_CompleteVO", order_CompleteVO);

	}

	// 주문 조회
	@Override
	public OrdersVO selectOrderById(int orderId) throws SQLException {
		log.info("==".repeat(50));
		return sqlSession.selectOne("com.choongang.shoppingmall.dao.OrdersDAO.selectOrderById", orderId);
	}

	 // 사용자 ID에 따른 주문 목록 조회
	
	@Override
	public List<OrdersVO> selectOrdersByUserId(Integer userId) {
	    log.info("사용자 ID: {}에 대한 주문 목록 조회 시작", userId);
	    List<OrdersVO> ordersList = sqlSession.selectList("com.choongang.shoppingmall.dao.OrdersDAO.selectOrdersByUserId", userId);
	    log.info("사용자 ID: {}에 대한 주문 목록 조회 완료, 주문 개수: {}", userId, ordersList.size());
	    return ordersList;
	}


	// 주문 ID에 따른 주문 완료 정보 조회
	@Override
	public List<Order_CompleteVO> selectOrderCompleteByOrderId(int orderId) {
	    log.info("주문 ID: {}에 대한 주문 완료 정보 조회 시작", orderId);
	    List<Order_CompleteVO> orderCompleteList = sqlSession.selectList("com.choongang.shoppingmall.dao.OrdersDAO.selectOrderCompleteByOrderId", orderId);
	    log.info("주문 ID: {}에 대한 주문 완료 정보 조회 완료, 결과 개수: {}", orderId, orderCompleteList.size());
	    return orderCompleteList;
	}


}
