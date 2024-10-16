package com.choongang.shoppingmall.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.UsersBoardDAO;
import com.choongang.shoppingmall.vo.AdminUsersPagingVO;
import com.choongang.shoppingmall.vo.UserVO;

import lombok.extern.slf4j.Slf4j;


@Service("usersBoardService")
@Slf4j
public class UsersBoardServiceImpl implements UsersBoardService {

	@Autowired
	private UsersBoardDAO usersBoardDAO;
	
	@Override
	public AdminUsersPagingVO<UserVO> getUserList(int currentPage, int sizeOfPage, int sizeOfBlock, String field, String search) {
		AdminUsersPagingVO<UserVO> pv = null;
		
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("field", field == null || field.trim().length()==0 ? null : field);
			map.put("search", search == null || search.trim().length()==0 ? null : search);
			int totalCount = usersBoardDAO.selectCount();
			pv = new AdminUsersPagingVO<>(totalCount, currentPage, sizeOfPage, sizeOfBlock);
			if(totalCount > 0) {
				map.put("startNo", pv.getStartNo()+"");
				map.put("endNo", pv.getEndNo()+"");
				pv.setList(usersBoardDAO.selectUserList(map));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		//log.info("pv 리턴 : {}",pv);
		return pv;
	}
	

	// 전체 회원 리스트
	@Override
	public List<UserVO> selectAll() {
		List<UserVO> list = null;
		try {
			list = usersBoardDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
