package com.choongang.shoppingmall.vo;


import java.util.Date;

import lombok.Data;

@Data
public class OrdersVO {
	
	private int order_id;			// 주문 번호
	private int user_id;			// 회원 번호
	private String merchant_uid;	// 가맹점 자체 고유번호
	private int total_price; 		// 총 가격
	private String payment_type;	// 결제 타입
	private String status;			// 주문 상태
	private String address;			// 주소
	private String request_type;	// 요청 사항
	private Date order_date;		// 주문 날짜
}
