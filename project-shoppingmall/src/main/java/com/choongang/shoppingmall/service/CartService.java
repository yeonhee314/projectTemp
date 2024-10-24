package com.choongang.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.CartMapper;
import com.choongang.shoppingmall.vo.CartVO;

@Service
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

    public List<CartVO> getCartItems(int userId) {
        return cartMapper.getCartItems(userId);
    }

  
}