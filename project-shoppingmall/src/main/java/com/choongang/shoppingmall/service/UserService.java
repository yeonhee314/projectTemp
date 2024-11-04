package com.choongang.shoppingmall.service;


import java.sql.SQLException;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.choongang.shoppingmall.vo.UserVO;

public interface UserService extends UserDetailsService{
	void insert(UserVO userVO);	//회원가입 메서드
	UserVO selectByUsername(String username);	//아이디로 찾기
	String selectByForgetUsername(String name, String email);  //아이디 찾기(이름과 이메일 받아서 처리)
	UserVO selectByUserPW(String username, String phone, String email);	//비밀번호찾기(사용자조회, 아이디,핸드폰,이메일 받아서 처리)
	void updatePassword(String username, String password);	//비밀번호찾기(새비밀번호변경)
	int selectCountByUsername(String username);	//아이디 중복확인
	int selectCountByEmail(String email);  // 이메일 중복확인
	int selectCountByNickname(String nickname);  // 닉네임 중복확인
	int selectCountByPhone(String phone);  // 핸드폰 중복확인
	
	UserVO getUserById(int userId) throws SQLException; //아이디로 회원정보 확인
	UserVO updateUser(UserVO userVO) throws SQLException;
}
