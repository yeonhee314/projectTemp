package com.choongang.shoppingmall.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.UserVO;

@Mapper
public interface UsersBoardDAO {
	int selectCount(HashMap<String, String> map) throws SQLException;
	UserVO selectByID(int user_id) throws SQLException;
	List<UserVO> selectUsers() throws SQLException;
	// 한 페이지 얻기
	List<UserVO> selectUserList(HashMap<String, String> map) throws SQLException;
}
