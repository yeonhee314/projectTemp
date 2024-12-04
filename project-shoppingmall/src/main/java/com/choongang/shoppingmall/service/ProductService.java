package com.choongang.shoppingmall.service;

import java.util.HashMap;
import java.util.List;

import com.choongang.shoppingmall.vo.AdminProductsPagingVO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ProductVO;

public interface ProductService {
	int selectProductCount(HashMap<String, String> map);
	// 한개 얻기
	ProductVO selectByProductId(int id);
	// 한 페이지 얻기
	PagingVO<ProductVO> getProductList(int currentPage, int sizeOfPage, int sizeOfBlock, String field, String search);
	AdminProductsPagingVO<ProductVO> getAdminProductList(int currentPage, int sizeOfPage, int sizeOfBlock, String field, String search);
	// 카테고리 id로 필터링한 상품 한 페이지 얻기
	PagingVO<ProductVO> getFilterProductList (int categoryId, int currentPage, int sizeOfPage, int sizeOfBlock);
	// 상품명 중복세기
	int selectCountByProductName(String product_name);
	String selectProductNameById(int product_id);
	int selectYCount();
	int selectNCount();
	int selectSoldOutCount();
	void insert(ProductVO productVO);
	void update(ProductVO productVO);
	void delete(int product_id);
	void updateStatus(ProductVO productVO);
	void updateStock(ProductVO productVO);
	
	PagingVO<ProductVO> searchProducts(int page, int size, String keyword);
	List<ProductVO> paginateProducts(List<ProductVO> productList, int page, int size, int totalCount);
	int calculateTotalPages(int totalCount, int size);
}
