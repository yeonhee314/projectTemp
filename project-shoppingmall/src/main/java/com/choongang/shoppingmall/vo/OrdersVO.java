package com.choongang.shoppingmall.vo;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrdersVO {
	
	private int order_id;			// 주문 번호
	private int user_id;			// 회원 번호
	private String merchant_uid;	// 가맹점 자체 고유번호
	private int total_price; 		// 총 가격
	private String payment_type;	// 결제 타입
	private String status;			// 주문 상태(결제 완료, 배송중, 배송완료)
	private String address;			// 주소
	private String request_type;	// 요청 사항
	private LocalDateTime order_date;		// 주문 날짜
	private String invoice;			// 운송장번호
	
	private List<Order_ItemVO> orderItems; // 주문 상품 리스트
	private String username;	// 조인컬럼
	private int itemcount;
	
	//포맷팅된 날짜 변환
	   public String getFormattedOrderDate() {
	        if (order_date != null) {
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            return order_date.format(formatter);
	        }
	        return "";
	    }

			
	}


