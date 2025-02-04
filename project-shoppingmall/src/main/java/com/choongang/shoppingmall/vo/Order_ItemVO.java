package com.choongang.shoppingmall.vo;

import lombok.Data;

@Data
public class Order_ItemVO {

	private int order_item_id;		// 주문 아이템 번호
	private int order_id;			// 주문 번호
	private int user_id;			// 회원 번호
	private int product_id;			// 제품 번호
	private int quantity;			// 수량
	private int order_price;		// 주문 아이템 가격
	private String review_status;	// 리뷰 가능한 상태 여부 : on, off
	private String product_option;	// 상품 옵션

	// 조인참조컬럼
	private String product_name;
	private String status;
	private int product_price;
	private int discount;
	private int itemcount;
	private int dis;
	private int btotal;
	private int bsum; // 할인 전 총가격
	private int sum; // 할인 전 총가격
	private int asum; // 할인 후 총가격
}
