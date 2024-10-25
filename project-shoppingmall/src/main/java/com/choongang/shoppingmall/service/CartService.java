package com.choongang.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.choongang.shoppingmall.dao.CartMapper;
import com.choongang.shoppingmall.vo.CartVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartService {

	@Autowired
	private CartMapper cartMapper;

	public void addCart(int userId, int productId, int cartCount, int productPrice, String productOption) {
		CartVO cartVO = new CartVO();
		cartVO.setUserId(userId);
		cartVO.setProductId(productId);
		cartVO.setCartCount(cartCount);
		cartVO.setProductPrice(productPrice);
		cartVO.setProductOption(productOption);

		cartMapper.addCart(cartVO);
	}
	
	public List<CartVO> getCartItems(int useId){
		return cartMapper.getCartItems(useId);
		
	}
	
	public void deleteCart(int cartId) {
		log.info("삭제 id : {}", cartId);
		cartMapper.deleteCart(cartId);
	}
	
	public void updateCart(CartVO cartVO) {
		cartMapper.updateCart(cartVO);
	}
	

}