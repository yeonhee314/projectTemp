package com.choongang.shoppingmall.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.ProductVO;

@Mapper
public interface ProductDAO {
	// 상품 갯수 세기
	int selectProductCount(HashMap<String, String> map) throws SQLException;
	// 필터링된 상품 갯수 세기
	int selectFilterProductCount(int category_id) throws SQLException;
	// 한개 얻기
	ProductVO selectByProductId(int product_id) throws SQLException;
	// 중복 이름 세기
	int selectCountByProductName(String product_name) throws Exception;
	// 한 페이지 얻기
	List<ProductVO> selectProductList(HashMap<String, String> map) throws SQLException;
	// 카테고리 id로 필터링한 상품 한 페이지 얻기
	List<ProductVO> selectFilterProductList (HashMap<String, Integer> map) throws SQLException;
	// 상품 아이디로 상품명 얻기
	String selectProductNameById(int product_id) throws SQLException;
	// 판매중인 상품 개수
	int selectYCount() throws SQLException;
	// 미판매 상품 개수
	int selectNCount() throws SQLException;
	// 품절된 상품 개수
	int selectSoldOutCount() throws SQLException;
	void insert(ProductVO productVO) throws SQLException;
	void update(ProductVO productVO) throws SQLException;
	void delete(int product_id) throws SQLException;
	void updateStatus(ProductVO productVO) throws SQLException;
	// 상품 재고 변경
	void updateStock(ProductVO productVO) throws SQLException;
}
