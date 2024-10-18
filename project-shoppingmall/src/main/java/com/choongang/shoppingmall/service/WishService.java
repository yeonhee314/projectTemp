package com.choongang.shoppingmall.service;

import com.choongang.shoppingmall.vo.WishVO;

public interface WishService {
	// wish list에 저장
	void addToWishList(WishVO wishVO);
}
