package com.choongang.shoppingmall.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.ReviewVO;
import com.choongang.shoppingmall.vo.UserVO;

@Mapper
public interface ReviewDAO {
	// 상품 id로 리뷰 가져오기
	ReviewVO selectReviewId(int id) throws Exception;
	
	// 상품 id별 리뷰 카운트 세기
	int selectReviewCount(int id) throws Exception;
	
	// 상품 id별 리스트 가져오기
	List<ReviewVO> selectReviewList(HashMap<String, Integer> map) throws Exception;
	
	// 평균 별점 계산해서 가져오기
	double selectRating(int id) throws Exception;
	
	// user id로 유저 정보 가져오기
	UserVO selectUserId(int id) throws Exception;
}
