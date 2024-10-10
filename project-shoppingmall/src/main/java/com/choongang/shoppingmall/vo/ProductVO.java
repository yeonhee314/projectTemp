package com.choongang.shoppingmall.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ProductVO {
	private int product_id;			// 상품 고유번호
	private int category_id;		// 카테고리 고유번호
	private String product_name;	// 상품 이름
	private String description;		// 상품 설명
	private String product_price;	// 상품 가격
	private String img1;			// 상품 사진 1
	private String img2;			// 상품 사진 2
	private String img3;			// 상품 사진 3
	private String thumb;			// 상품 썸네일
	private int product_stoc;		// 상품 재고
	private int discount;			// 할인율
	private Date product_regDate;	// 상품 등록일
	private String product_status;	// 상품 옵션
}
