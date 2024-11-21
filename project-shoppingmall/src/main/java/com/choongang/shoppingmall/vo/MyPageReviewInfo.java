package com.choongang.shoppingmall.vo;

import org.springframework.beans.factory.annotation.Autowired;

import com.choongang.shoppingmall.service.ProductService;

import lombok.Data;

@Data
public class MyPageReviewInfo {
	private int order_id;
	private int product_id;
	private int quantity;
	private int order_price;
	
	@Autowired
	private ProductService productService;
	
	public ProductVO getProductVO(int product_id) {
		return productService.selectByProductId(product_id);
	}
}
