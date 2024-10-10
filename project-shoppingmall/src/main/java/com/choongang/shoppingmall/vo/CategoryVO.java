package com.choongang.shoppingmall.vo;

import lombok.Data;

@Data
public class CategoryVO {
	private int category_id;		// 카테고리 고유 번호 : 1-Women, 2-Men, 3-Bag, 4-Shoes, 5-Watches
	private String category_name;	// 카테고리명
}
