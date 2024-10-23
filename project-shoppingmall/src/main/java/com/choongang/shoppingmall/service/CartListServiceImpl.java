package com.choongang.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.CartMapper;
import com.choongang.shoppingmall.vo.CartItem;

@Service
public class CartListServiceImpl implements CartListService{
	
	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public List<CartItem> getCartItems(int useId){
		return cartMapper.getCartItems(useId);
		
	}
	

}
