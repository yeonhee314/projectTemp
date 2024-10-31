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
			//userVO.setUser_role("ROLE_USER");
			try {
				userDAO.insert(userVO);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			log.info("회원가입 성공: {}", userVO);
		}
	}
	
	// 아이디 찾기
	@Override
	public String selectByForgetUsername(String name, String email) {
	    log.info("아이디 찾기 요청: 이름 = {}, 이메일 = {}", name, email);
	    String username = null;
	    
	    try {
	        username = userDAO.selectByForgetUsername(name, email);
	        // 아이디가 존재하지 않는 경우 로그 남기기
	        if (username == null) {
	            log.warn("정보와 일치하는 아이디가 없습니다. 이름: {}, 이메일: {}", name, email);
	            return null;
	        } else {
	            // 아이디 찾기 성공 로그
	            log.info("아이디 찾기 성공: 이름 = {}, 이메일 = {}, 아이디 = {}", name, email, username);
	            return username;
	        }
	    } catch (Exception e) {
	    	log.error("아이디 찾기 중 오류 발생: {}", e.getMessage());
	        e.printStackTrace();
	        return null;
	    }
	}
	
	//아이디 중복 확인
	@Override
	public int selectCountByUsername(String username) {
		int countUsername = 1;
		try {
			countUsername = userDAO.selectCountByUsername(username);
		} catch (SQLException e) {
			log.error("아이디 중복 확인 중 오류 발생: {}", e.getMessage());
			e.printStackTrace();
		}
		return countUsername;
	}
	//이메일 중복 확인
	@Override
	public int selectCountByEmail(String email) {
		int countEmail = 1;
		try {
			countEmail = userDAO.selectCountByEmail(email);
		} catch (SQLException e) {
			log.error("이메일 중복 확인 중 오류 발생: {}", e.getMessage());
			e.printStackTrace();
		}
		return countEmail;
	}
	
	//닉네임 중복 확인
	@Override
	public int selectCountByNickname(String nickname) {
		int countNick = 1;
		try {
			countNick = userDAO.selectCountByNickname(nickname);
		} catch (SQLException e) {
			log.error("닉네임 중복 확인 중 오류 발생: {}", e.getMessage());
			e.printStackTrace();
		}
		return countNick;
	}
	//핸드폰 중복 확인
	@Override
	public int selectCountByPhone(String phone) {
		int countPhone = 1;
		try {
			countPhone = userDAO.selectCountByPhone(phone);
		} catch (SQLException e) {
			log.error("핸드폰 중복 확인 중 오류 발생: {}", e.getMessage());
			e.printStackTrace();
		}
		return countPhone;
	}
	
	//아이디로 회원정보 확인
	@Override
	public UserVO getUserById(int userId) throws SQLException {
	
			return userDAO.getUserById(userId);
		
	}
	//회원정보 수정
	@Transactional
	public UserVO updateUser(UserVO userVO) throws SQLException {
	
			userDAO.updateUser(userVO);
			return userVO;
		
	}
	
	
	
}
