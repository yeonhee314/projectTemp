package com.choongang.shoppingmall.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.UserVO;

@Mapper
public interface UsersBoardDAO {
	int selectCount() throws SQLException;
	List<UserVO> selectAll() throws SQLException;
	UserVO selectByID(int user_id) throws SQLException;
}
