package com.choongang.shoppingmall.service;

import java.util.List;

import com.choongang.shoppingmall.vo.CategoryVO;
import com.choongang.shoppingmall.vo.PagingVO;

public interface CategoryService {
	// 카테고리 모두 가져오기
	List<CategoryVO> selectCategory();
	// 카테고리 이름 모두 가져오기
	List<CategoryVO> selectCategoryName();
	// id로 카테고리 가져오기
	CategoryVO selectCategoryId(int id);
	// 한 페이지 얻기
	PagingVO<CategoryVO> getCategoryList(int currentPage, int sizeOfPage, int sizeOfBlock);
}
