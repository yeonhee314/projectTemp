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
	//아이디 찾기(이름과 이메일을 받아서 처리)
	String selectByForgetUsername(String name, String email) throws SQLException;
	//비밀번호찾기(사용자조회 : 아이디,핸드폰,이메일 받아서 처리)
	UserVO selectByUserPW(String username, String phone, String email) throws SQLException;
	//비밀번호찾기(새비밀번호변경)
	void updatePassword(String username, String password) throws SQLException;
	// 동일한 아이디의 개수를 센다.(아이디 중복확인)
	int selectCountByUsername(String username) throws SQLException;
	// 동일한 이메일의 개수를 센다.(이메일 중복확인)
	int selectCountByEmail(String email) throws SQLException;
	// 동일한 닉네임의 개수를 센다.(닉네임 중복확인)
	int selectCountByNickname(String nickname) throws SQLException;
	// 동일한 핸드폰의 개수를 센다.(핸드폰 중복확인)
	int selectCountByPhone(String phone) throws SQLException;
	
	// id로 유저 정보 확인
	UserVO getUserById(int userId)throws SQLException;
	UserVO selectUserById(int user_id) throws SQLException;
	// 유저 정보 업데이트
	void updateUser(UserVO userVO);
	// 유저 포인트 증감
	void pointUpdate(UserVO userVO) throws SQLException;
}
