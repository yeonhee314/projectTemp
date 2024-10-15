package com.choongang.shoppingmall.service;

import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ReviewVO;
import com.choongang.shoppingmall.vo.UserVO;

public interface ReviewService {
	// 상품 id로 리뷰 가져오기
	ReviewVO selectReviewId(int id);
	
	// 상품 id별 리뷰 갯수 세기
	int selectReviewCount(int id);
	
	// 상품 id별 리스트 가져오기
	PagingVO<ReviewVO> getReviewList(int id, int currentPage, int sizeOfPage, int sizeOfBlock);
	
	// 평균 별점 계산해서 가져오기
	double selectRating(int id);
	
	// user id로 유저 정보 가져오기
	UserVO selectUserId(int id);
}
