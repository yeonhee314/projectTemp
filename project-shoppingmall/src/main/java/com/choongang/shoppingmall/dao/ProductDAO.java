package com.choongang.shoppingmall.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.ProductVO;

@Mapper
public interface ProductDAO {
	// 회원
	// 1. 상품 갯수 세기
	int selectProductCount() throws SQLException;
	// 2. 한개 얻기
	ProductVO selectByProductId(int id) throws SQLException;
	// 3. 한 페이지 얻기
	List<ProductVO> selectProductList(HashMap<String, Integer> map) throws SQLException;
	
	// 관리자
	// 상품 추가
	// 상품 수정
	// 상품 삭제
}
