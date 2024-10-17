package com.choongang.shoppingmall.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartItem {
	
	private int cartId;
	private int userId;
	private int productId;     // 상품 ID
    private String productName; // 상품 이름
    private int productPrice;  // 상품 가격
    private int cartCount;     // 장바구니에 담긴 수량

   
}