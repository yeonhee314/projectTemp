package com.choongang.shoppingmall.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.ReviewVO;

@Mapper
public interface ReviewDAO {
	// 상품 id로 리뷰 가져오기
	ReviewVO selectReviewId(int id) throws Exception;
	
	// 상품 id별 리뷰 카운트 세기
	int selectReviewCount(int id) throws Exception;
	
	// 상품 id별 리스트 가져오기
	List<ReviewVO> selectReviewList(HashMap<String, Integer> map) throws Exception;
}
