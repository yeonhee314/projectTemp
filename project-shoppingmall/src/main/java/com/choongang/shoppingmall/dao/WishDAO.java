package com.choongang.shoppingmall.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.ProductVO;
import com.choongang.shoppingmall.vo.WishVO;

@Mapper
public interface WishDAO {
	void addToWishList(WishVO vo) throws SQLException;
	void deleteToWishList(WishVO vo) throws SQLException;
	List<WishVO> selectWishByUserId(int user_id) throws SQLException;
	int isWishCount(HashMap<String, Integer> map) throws SQLException;
	List<ProductVO> selectProductByUserId(int user_id) throws SQLException;
}
