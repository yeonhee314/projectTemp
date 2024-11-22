package com.choongang.shoppingmall.vo;

import lombok.Data;

@Data
public class Order_ItemVO {
<<<<<<< HEAD
	private int order_item_id;	// 주문 아이템 번호
	private int order_id;		// 주문 번호
	private int user_id;		// 회원 번호
	private int product_id;		// 제품 번호
	private int quantity;		// 수량
	private int order_price;	// 주문 아이템 가격
	
	// 조인참조컬럼
	private String product_name;
	private int product_price;
	private int discount;
	private String product_option;
	private int sum; // 아이템별 총가격
=======
	private int order_item_id;		// 주문 아이템 번호
	private int order_id;			// 주문 번호
	private int user_id;			// 회원 번호
	private int product_id;			// 제품 번호
	private int quantity;			// 수량
	private int order_price;		// 주문 아이템 가격
	private String review_status;	// 리뷰 가능한 상태 여부 : on, off
	private String product_option;	// 상품 옵션
>>>>>>> d516dbe689b9b80d62a31ad37cd868cd4922d503
}
