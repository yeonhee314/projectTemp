package com.choongang.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.CategoryDAO;
import com.choongang.shoppingmall.vo.CategoryVO;

import lombok.extern.slf4j.Slf4j;

@Service("categoryService")
@Slf4j
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryDAO categoryDAO;

	@Override
	public List<CategoryVO> selectCategory() {
		List<CategoryVO> categoryList = null;
		try {
			categoryList = categoryDAO.selectCategory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return categoryList;
	}
	@Override
	public List<CategoryVO> selectCategoryName() {
		List<CategoryVO> categoryNameList = null;
		try {
			categoryNameList = categoryDAO.selectCategoryName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return categoryNameList;
	}

	@Override
	public CategoryVO selectCategoryId(int id) {
		CategoryVO categoryVO = null;
		try {
			categoryVO = categoryDAO.selectCategoryId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return categoryVO;
	}
	
	
}
