package com.choongang.shoppingmall.service;

import java.util.List;

import com.choongang.shoppingmall.vo.AdminUsersPagingVO;
import com.choongang.shoppingmall.vo.UserVO;


public interface UsersBoardService {
	int selectCount();
	UserVO selectByID(int user_id);
	List<UserVO> selectAll();
	AdminUsersPagingVO<UserVO> getUserList(int currentPage, int sizeOfPage, int sizeOfBlock, String field, String search);
}
