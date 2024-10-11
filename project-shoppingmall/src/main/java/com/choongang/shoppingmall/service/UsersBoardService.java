package com.choongang.shoppingmall.service;

import java.util.List;

import com.choongang.shoppingmall.vo.UserVO;


public interface UsersBoardService {
	int selectCount();
	UserVO selectByID(int user_id);
	List<UserVO> selectAll();
}
