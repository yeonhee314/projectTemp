package com.choongang.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.choongang.shoppingmall.dao.CartMapper;
import com.choongang.shoppingmall.vo.CartItem;

@Service
public class CartService {

	@Autowired
	private CartMapper cartMapper;

	public void addCart(int userId, int productId, int cartCount, int productPrice, String productOption) {
		CartItem cartItem = new CartItem();
		cartItem.setUserId(userId);
		cartItem.setProductId(productId);
		cartItem.setCartCount(cartCount);
		cartItem.setProductPrice(productPrice);
		cartItem.setProductOption(productOption);

		cartMapper.addCart(cartItem);
	}
	//사용자 장바구니 리스트 가져오는 메소드
	public List<CartItem> getCartItems(int userId) {
		
		  return cartMapper.getCartItems(userId);
	}

}