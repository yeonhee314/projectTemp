package com.choongang.shoppingmall.controller;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.choongang.shoppingmall.service.OrderService;
import com.choongang.shoppingmall.vo.Order_DetailVO;
import com.choongang.shoppingmall.vo.OrdersVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/orders") // 기본 URL
@Slf4j
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	// 주문서 작성
	public ResponseEntity<String> createOrder(@RequestBody OrdersVO ordersVO) {
		log.info("주문서 작성 요청 시작: {}", ordersVO); // 요청 시작 로그
		try {
			// 주문 정보 저장
			orderService.createOrder(ordersVO);
			// 주문 상세 정보 저장
			Order_DetailVO orderDetail = new Order_DetailVO();
			orderDetail.setOrder_id(ordersVO.getOrder_id()); // 주문 ID 설정
			orderDetail.setQuantity(ordersVO.getCount()); // 수량 설정
			orderDetail.setTotal_price(ordersVO.getPrice()); // 총 가격 설정

			orderService.createOrder_Detail(orderDetail); // 주문 상세 저장

			log.info("주문서 작성 요청 성공: {}", ordersVO); // 성공 로그
			return new ResponseEntity<>("주문서 작성 성공", HttpStatus.CREATED);
		} catch (SQLException e) {
			log.error("주문서 작성 요청 실패: {}", e.getMessage());
			return new ResponseEntity<>("주문서 작성 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 주문 조회
	@GetMapping("/{orderId}")
	public ResponseEntity<OrdersVO> getorder(@PathVariable int orderId) {
		log.info("주문 조회 요청 시작: 주문 ID = {}", orderId); // 요청 시작 로그
		try {
			OrdersVO ordersVO = orderService.getOrderByid(orderId);
			log.info("주문 조회 성공: {}", ordersVO); // 요청 시작 로그
			if (ordersVO != null) {
				return new ResponseEntity<>(ordersVO, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 주문이 없는 경우
			}

		} catch (SQLException e) {
			log.error("주문 조회 실패: {}", e.getMessage());	// 요청 시작 로그
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);	// 서버 에러
		}
	}

	@GetMapping("/orders.html")
	public String showOrderPage() {
		return "orders";	// orders.html 뷰 이름 반환
	}
}
