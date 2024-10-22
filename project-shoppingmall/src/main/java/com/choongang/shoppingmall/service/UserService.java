package com.choongang.shoppingmall.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.choongang.shoppingmall.vo.UserVO;

public interface UserService extends UserDetailsService{
	void insert(UserVO userVO);	//회원가입 메서드
	UserVO selectByUsername(String username);	//아이디로 찾기
	String selectByForgetUsername(String name, String email);  //아이디 찾기(이름과 이메일 받아서 처리)
	int selectCountByUsername(String username);	//아이디 중복확인
	//int selectCountByEmail(String email);  // 이메일 중복확인
	int selectCountByNickname(String nickname);  // 닉네임 중복확인
	int selectCountByPhone(String phone);  // 핸드폰 중복확인
}
