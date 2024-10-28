package com.choongang.shoppingmall.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.CartMapper;
import com.choongang.shoppingmall.dao.ProductDAO;
import com.choongang.shoppingmall.vo.CartVO;
import com.choongang.shoppingmall.vo.ProductVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductDAO productDAO;

    public void addCart(int userId, int productId, int cartCount, int productPrice, String productOption) {
        CartVO cartVO = new CartVO();
        cartVO.setUserId(userId);
        cartVO.setProductId(productId);
        cartVO.setCartCount(cartCount);
        cartVO.setProductPrice(productPrice);
        cartVO.setProductOption(productOption);
        
        cartMapper.addCart(cartVO);
    }

    public List<CartVO> getCartItems(int userId) throws SQLException {
        List<CartVO> cartItems = cartMapper.getCartItems(userId);
      
        for (CartVO cartItem : cartItems) {
            //System.out.println("상품정보: " + cartItem + ", ProductId: " + cartItem.getProductId());
            ProductVO product = productDAO.selectByProductId(cartItem.getProductId());
            if (product != null) {
                cartItem.setProduct(product);
            } else {
                System.out.println("No product found for ID: " + cartItem.getProductId());
                cartItem.setProduct(new ProductVO());
            }
        }
        return cartItems;
    }

    public void deleteCart(int cartId) {
		log.info("삭제 id : {}", cartId);
		cartMapper.deleteCart(cartId);
	}
	
	public void updateCart(CartVO cartVO) {
		cartMapper.updateCart(cartVO);
	}
}