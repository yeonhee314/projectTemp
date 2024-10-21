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

    public List<CartItem> getCartByUserId(int userId) {
        return cartMapper.getCartByUserId(userId);
    }

    public void deleteCartItem(int cartId) {
        cartMapper.deleteCartItem(cartId);
    }
}