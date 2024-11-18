package com.choongang.shoppingmall.vo;

import lombok.Data;

@Data
public class Order_ItemVO {
	private int order_item_id;	// 주문 아이템 번호
	private int order_id;		// 주문 번호
	private int user_id;		// 회원 번호
	private int product_id;		// 제품 번호
	private int quantity;		// 수량
	private int order_price;	// 주문 아이템 가격
}
