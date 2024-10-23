package com.choongang.shoppingmall.service;

import java.util.List;

import com.choongang.shoppingmall.vo.CartItem;


public interface CartListService {
	
	 List<CartItem> getCartItems(int userId);
}