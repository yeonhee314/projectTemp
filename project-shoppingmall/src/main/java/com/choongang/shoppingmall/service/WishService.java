package com.choongang.shoppingmall.service;

import java.util.List;

import com.choongang.shoppingmall.vo.WishVO;

public interface WishService {
	// 위시 리스트에 저장
	void addToWishList(WishVO wishVO);
	// 위시 리스트에 삭제
	void deleteToWishList(WishVO wishVO);
	// 유저 아이디별 위시리스트 불러오기
	List<WishVO> selectWishByUserId(int user_id);
	// 찜 갯수로 찜 상태 판단
	int isWishCount(int user_id, int product_id);
}
