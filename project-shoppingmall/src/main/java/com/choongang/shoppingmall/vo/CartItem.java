package com.choongang.shoppingmall.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartItem {
	
	private int cartId;  // 장바구니 고유번호
	private int userId;	// 회원 고유번호
	private int productId;     // 상품 ID
	private int cartCount;     // 장바구니에 담긴 수량
	private int cartPrice; // 장바구니 총금액
	
    private String productName; // 상품 이름
    private String productOption; // 상품 옵션
    
    private int productPrice;  // 상품 가격
    

   
}