package com.choongang.shoppingmall.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.WishVO;

@Mapper
public interface WishDAO {
	void addToWishList(WishVO vo) throws SQLException;
}
