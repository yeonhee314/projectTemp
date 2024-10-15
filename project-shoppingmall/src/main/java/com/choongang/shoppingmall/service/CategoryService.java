package com.choongang.shoppingmall.service;

import java.util.List;

import com.choongang.shoppingmall.vo.AdminCategoryPagingVO;
import com.choongang.shoppingmall.vo.CategoryVO;

public interface CategoryService {
	// 카테고리 모두 가져오기
	List<CategoryVO> selectCategory();
	// 카테고리 이름 모두 가져오기
	List<CategoryVO> selectCategoryName();
	// 카테고리 이름 개수 (중복확인)
	int selectCountByCategoryName(String category_name);
	// id로 카테고리 가져오기
	CategoryVO selectCategoryId(int id);
	// 한 페이지 얻기
	AdminCategoryPagingVO<CategoryVO> getCategoryList(int currentPage, int sizeOfPage, int sizeOfBlock);
	void insert(CategoryVO categoryVO);
}
