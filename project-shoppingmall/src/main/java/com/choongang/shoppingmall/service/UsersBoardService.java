package com.choongang.shoppingmall.service;

import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.UserVO;


public interface UsersBoardService {
	PagingVO<UserVO> getList(int currentPage, int sizeOfPage, int sizeOfBlock);
	int selectCount();
}
