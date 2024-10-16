package com.choongang.shoppingmall.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choongang.shoppingmall.dao.UserDAO;
import com.choongang.shoppingmall.vo.UserVO;

import lombok.extern.slf4j.Slf4j;

@Service("userService")
@Slf4j
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("로그인 시도: {}", username);
		UserVO vo = null;
		try {
			vo = userDAO.selectByUsername(username);
			if(vo == null) {
				throw new UsernameNotFoundException("지정 아이디가 존재하지 않습니다.");
			}
		} catch (SQLException e) {
			log.error("로그인 에러 발생: {}", e.getMessage());
			e.printStackTrace();
		}
		log.info("로그인 성공: {}", vo);
		return vo;
	}
	
	//username으로 찾기
	@Override
	public UserVO selectByUsername(String username) {
		UserVO userVO = null;
		try {
			userVO = userDAO.selectByUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userVO;
	}
	
	//회원가입
	@Override
	public void insert(UserVO userVO) {
		log.info("회원가입 시도: {}", userVO);
		if(userVO != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
			userVO.setUser_role("ROLE_USER");
			try {
				userDAO.insert(userVO);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			log.info("회원가입 성공: {}", userVO);
		}
	}
	
	//아이디 중복 확인
	@Override
	public int selectCountByUsername(String username) {
		int count = 1;
		try {
			count = userDAO.selectCountByUsername(username);
		} catch (SQLException e) {
			log.error("아이디 중복 확인 중 오류 발생: {}", e.getMessage());
			e.printStackTrace();
		}
		return count;
	}
	
}
