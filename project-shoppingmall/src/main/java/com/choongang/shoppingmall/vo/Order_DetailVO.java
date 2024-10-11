package com.choongang.shoppingmall.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Order_DetailVO {

	private int order_detail_id;	// 주문상세 고유번호
	private Date paid_date;			// 결제날짜
	private int total_price;		// 총 결제 가격
	private int quantity;			// 상품 수량
	private String order_status;	// 주문 상태 
	private String paid_option;		// 주문 옵션
	private String paid_name;		// 구매자 이름
	private String paid_addr;		// 구매자 주소
	private String product_name; 	// 상품 이름
	private String thumb;			// 상품 썸네일
	private String product_status;	// 상품 옵션
	private int product_price; 		// 상품 가격
	private int discount;			// 할인율
	private String pay_method;		// 결제 수단
	
	// 외래키 
	private int order_id; 			// 주문 번호
	private int user_id;			// 회원 고유번호
	private int product_id;			// 상품 고유번호
}
