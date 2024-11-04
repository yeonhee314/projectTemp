package com.choongang.shoppingmall.vo;

import java.util.Date;

import lombok.Data;

@Data
public class OrdersVO {

	private int order_id; // 주문 번호
	private int price;	// 가격
	private int count;	// 수량
	private String zipcode;	// 우편 번호
	private String addr;	// 주소
	private String addr_detail;	// 상세 주소
	private String req;	// 요청사항
	private String paysummary;	// 결제 정보
	private String payment;	// 결제 수단
	private Date order_date;	// 주문 날짜
	// 외래 키
	private int user_id;		// 회원 고유번호
	private int product_id;		// 상품 고유번호
	private int category_id;	// 카테고리 고유번호
	private int cartId;			// 장바구니 고유번호
}
