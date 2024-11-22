package com.choongang.shoppingmall.vo;

import java.util.Date;

import lombok.Data;

@Data
public class MyPageReviewInfo {
	private int order_id;
	private int order_item_id;
	private int product_id;
	private int quantity;
	private int order_price;
	private Date order_date;
	private String review_status;	// 리뷰를 쓸 수 있는 상태 : on, off
	private String product_option;
}
