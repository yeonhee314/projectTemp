package com.choongang.shoppingmall.dao;


import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.UserVO;

@Mapper
public interface UserDAO {
	//회원가입
	void insert(UserVO userVO) throws SQLException;
	// 1개얻기(username로 얻기)
	UserVO selectByUsername(String username) throws SQLException;
	// 동일한 아이디의 개수를 센다.(아이디 중복확인)
	int selectCountByUsername(String username) throws SQLException;
}
