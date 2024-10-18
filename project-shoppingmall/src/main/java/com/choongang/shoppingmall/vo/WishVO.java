package com.choongang.shoppingmall.vo;

import java.util.Date;

import lombok.Data;

@Data
public class WishVO {
	private int wish_id; // 찜 고유번호
	private int user_id; // 회원 고유번호
	private int product_id; // 상품 고유번호
	private Date wish_date;	// 찜한 날짜
}
