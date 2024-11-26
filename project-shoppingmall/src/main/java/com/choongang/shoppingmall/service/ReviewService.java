package com.choongang.shoppingmall.service;

import java.util.HashMap;

import com.choongang.shoppingmall.vo.AdminProductsPagingVO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ReviewVO;
import com.choongang.shoppingmall.vo.UserVO;

public interface ReviewService {
	int selectReviewTotalCount(HashMap<String, String> map);
	int selectReviewCountByUserId(int user_id);
	// 상품 id로 리뷰 가져오기
	ReviewVO selectReviewId(int id);
	ReviewVO selectReviewByReviewId(int review_id);
	
	PagingVO<ReviewVO> selectReviewPage(int currentPage, int sizeOfPage, int sizeOfBlock, String field, String search);
	AdminProductsPagingVO<ReviewVO> selectAdminReviewPage(int currentPage, int sizeOfPage, int sizeOfBlock, String field, String search);
	// 상품 id별 리뷰 갯수 세기
	int selectReviewCount(int id);
	
	// 상품 id별 리스트 가져오기
	PagingVO<ReviewVO> getReviewList(int id, int currentPage, int sizeOfPage, int sizeOfBlock);
	
	// 평균 별점 계산해서 가져오기
	double selectRating(int id);
	
	// user id로 유저 정보 가져오기
	UserVO selectUserId(int id);
	// 리뷰 작성하기
	void addToReview(ReviewVO vo);
}
