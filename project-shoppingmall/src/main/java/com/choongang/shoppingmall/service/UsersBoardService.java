package com.choongang.shoppingmall.service;

import java.util.HashMap;

import com.choongang.shoppingmall.vo.AdminUsersPagingVO;
import com.choongang.shoppingmall.vo.UserVO;


public interface UsersBoardService {
	int selectCount(HashMap<String, String> map);
	UserVO selectByID(int user_id);
	AdminUsersPagingVO<UserVO> getUserList(int currentPage, int sizeOfPage, int sizeOfBlock, String field, String search);
}
