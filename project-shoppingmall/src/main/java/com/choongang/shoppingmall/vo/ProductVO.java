package com.choongang.shoppingmall.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ProductVO {
	private int product_id;			// 상품 고유번호
	private int category_id;		// 카테고리 고유번호
	private String product_name;	// 상품 이름
	private String description;		// 상품 설명
	private int product_price;		// 상품 가격
	private int img_count;			// 상품 사진 갯수
	private int product_stock;		// 상품 재고
	private int discount;			// 할인율
	private Date product_regDate;	// 상품 등록일
	private String product_status;	// 판매 가능 상태
	private String product_option;	// 상품 옵션
	
	// 할인가 계산
	public int getDiscountPrice(int price, int discount) {
		return price - (price * discount / 100);
	}
}


