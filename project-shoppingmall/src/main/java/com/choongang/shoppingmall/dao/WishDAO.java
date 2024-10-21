package com.choongang.shoppingmall.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.WishVO;

@Mapper
public interface WishDAO {
	void addToWishList(WishVO vo) throws SQLException;
	List<WishVO> selectWishByUserId(int user_id) throws SQLException;
}
