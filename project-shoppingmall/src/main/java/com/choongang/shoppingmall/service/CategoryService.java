package com.choongang.shoppingmall.service;

import java.util.List;

import com.choongang.shoppingmall.vo.CategoryVO;

public interface CategoryService {
	// 카테고리 모두 가져오기
	List<CategoryVO> selectCategory();
	
	// id로 카테고리 가져오기
	CategoryVO selectCategoryId(int id);
}
