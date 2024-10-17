package com.choongang.shoppingmall.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.CategoryDAO;
import com.choongang.shoppingmall.vo.AdminCategoryPagingVO;
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
	// 카테고리 페이징
	@Override
	public AdminCategoryPagingVO<CategoryVO> getCategoryList(int currentPage, int sizeOfPage, int sizeOfBlock) {
		AdminCategoryPagingVO<CategoryVO> cv =null;
		
		try {
			int totalCount = categoryDAO.selectCategoryCount();
			cv = new AdminCategoryPagingVO<>(totalCount, currentPage, sizeOfPage, sizeOfBlock);
			if(totalCount > 0) {
				HashMap<String, Integer> map = new HashMap<>();
				map.put("startNo", cv.getStartNo());
				map.put("endNo", cv.getEndNo());
				cv.setList(categoryDAO.selectCategoryList(map));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		//log.info("cv 리턴 : {}",cv);
		return cv;
	}
	@Override
	public int selectCountByCategoryName(String category_name) {
		int count = 1;
		try {
			count = categoryDAO.selectCountByCategoryName(category_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public void insert(CategoryVO categoryVO) {
		try {
			categoryDAO.insert(categoryVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("카테고리 저장 리턴 : {}",categoryVO);
	}
	@Override
	public void update(CategoryVO categoryVO) {
		log.info("카테고리 업데이트 요청 : {}",categoryVO);
		try {
			categoryDAO.update(categoryVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("카테고리 업데이트 리턴 : {}",categoryVO);
		
	}
	@Override
	public void deleteById(CategoryVO categoryVO) {
		try {
			categoryDAO.deleteById(categoryVO.getCategory_id());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("카테고리(id) 삭제 리턴 : {}",categoryVO.getCategory_id());
	}
	@Override
	public void deleteByName(CategoryVO categoryVO) {
		try {
			categoryDAO.deleteByName(categoryVO.getCategory_name());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("카테고리 삭제(name) 리턴 : {}",categoryVO.getCategory_name());
	}
}
