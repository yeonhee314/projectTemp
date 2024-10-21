package com.choongang.shoppingmall.vo;

import lombok.Data;

@Data
public class CartVO {
	private int cart_id; // 장바구니 고유번호
	private int user_id; // 회원 고유번호
	private int product_id; // 상품 고유번호
	private int cart_count; // 수량
	private int cart_price; // 장바구니 총금액
	
	private String product_name; // 상품 이름
	private String product_option; // 상품 옵션
	
	private int product_price; // 상품 가격

}
