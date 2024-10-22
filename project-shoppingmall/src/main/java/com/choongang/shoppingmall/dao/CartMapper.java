package com.choongang.shoppingmall.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.choongang.shoppingmall.vo.CartItem;

@Mapper
public interface CartMapper {

	// 장바구니에 상품 추가
	void addCart(CartItem cartItem);
    
    // 사용자의 장바구니 목록 조회
    List<CartItem> getCartItems(int userId);
      
    
}