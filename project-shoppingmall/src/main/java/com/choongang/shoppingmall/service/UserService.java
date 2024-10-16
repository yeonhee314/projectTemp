package com.choongang.shoppingmall.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.choongang.shoppingmall.vo.UserVO;

public interface UserService extends UserDetailsService{
	void insert(UserVO userVO);	//회원가입 메서드
	UserVO selectByUsername(String username);	//아이디로 찾기
	int selectCountByUsername(String username);	//아이디 중복확인
}
