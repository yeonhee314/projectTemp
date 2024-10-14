package com.choongang.shoppingmall.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.CategoryDAO;
import com.choongang.shoppingmall.vo.CategoryVO;
import com.choongang.shoppingmall.vo.PagingVO;

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
	@Override
	public PagingVO<CategoryVO> getCategoryList(int currentPage, int sizeOfPage, int sizeOfBlock) {
		PagingVO<CategoryVO> cv =null;
		
		try {
			int totalCount = categoryDAO.selectCategoryCount();
			cv = new PagingVO<>(totalCount, currentPage, sizeOfPage, sizeOfBlock);
			if(totalCount > 0) {
				HashMap<String, Integer> map = new HashMap<>();
				map.put("startNo", cv.getStartNo());
				map.put("endNo", cv.getEndNo());
				cv.setList(categoryDAO.selectCategoryList(map));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		log.info("cv 리턴 : {}",cv);
		return cv;
	}
	
	
}
