package com.choongang.shoppingmall.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.UsersBoardDAO;
import com.choongang.shoppingmall.vo.UserVO;

import lombok.extern.slf4j.Slf4j;

/*
3. public MemberVO loadUserByUsername(String username) throws UsernameNotFoundException {}
   위 메서드에서 리턴 타입을 UserDetails을 구현한 VO로 바꿔주고 DAO에서  Userid로 VO를 얻어 리턴한다.

*/
@Service("usersBoardService")
@Slf4j
public class UsersBoardServiceImpl implements UsersBoardService {

	@Autowired
	private UsersBoardDAO usersBoardDAO;
	
	// 전체 회원 리스트
	@Override
	public List<UserVO> selectAll() {
		List<UserVO> list = null;
		try {
			list = usersBoardDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("list 인수 : {}",list);
		return list;
	}
	
	// 총 회원 수
	@Override
	public int selectCount() {
		int count = 1;
		try {
			count = usersBoardDAO.selectCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	// 회원 정보 상세
	@Override
	public UserVO selectByID(int user_id) {
		UserVO userVO = null;
		try {
			userVO = usersBoardDAO.selectByID(user_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userVO;
	}
}
