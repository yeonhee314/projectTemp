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
	
	// 참조 컬럼
	private String category_name;
	private String sc; // 재고 변경 파라미터
	// 할인가 계산
	public int getDiscountPrice(int price, int discount) {
		return price - (price * discount / 100);
	}
	
	//상품 별점 계산
	public String getStarRating(double rating) {
		StringBuffer sb = new StringBuffer();
		int fullStar = (int)Math.floor(rating);
		boolean hasHalfStar = (rating - fullStar) >= 0.5;
		int emptyStar = 5 - (int)fullStar - (hasHalfStar ? 1 : 0);
		
		for(int i = 0; i < fullStar; i++) {
			sb.append("<i class='zmdi zmdi-star'>");
			sb.append("</i>");
		}
		
		if(hasHalfStar) {
			sb.append("<i class='zmdi zmdi-star-half'>");
			sb.append("</i>");
		}
		
		for(int i = 0; i < emptyStar; i++) {
			sb.append("<i class='zmdi zmdi-star-outline'>");
			sb.append("</i>");
		}
		
		return sb.toString();
	}
}


