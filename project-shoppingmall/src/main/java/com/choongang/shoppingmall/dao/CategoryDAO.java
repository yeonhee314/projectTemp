package com.choongang.shoppingmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.CategoryVO;

@Mapper
public interface CategoryDAO {
	// 카테고리 모두 가져오기
	List<CategoryVO> selectCategory() throws Exception;
	// 카테고리 이름 모두 가져오기
	List<CategoryVO> selectCategoryName() throws Exception;
	// id로 카테고리 가져오기
	CategoryVO selectCategoryId(int id) throws Exception;
}
