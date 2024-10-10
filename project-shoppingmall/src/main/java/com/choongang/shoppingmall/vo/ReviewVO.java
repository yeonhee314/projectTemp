package com.choongang.shoppingmall.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ReviewVO {
	private int review_id;			// 후기 번호
	private int user_id;			// 회원 고유번호
	private int category_id;		// 카테고리 고유번호
	private String review_title;	// 리뷰 제목
	private String review_text;		// 리뷰 내용
	private String review_rating;	// 별점
	private Date review_date;		// 리뷰 작성일
	private String review_img;		// 리뷰 사진
	private int review_count;		// 후기 갯수
}
