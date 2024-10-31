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
			int totalCount = usersBoardDAO.selectCount(map);
			pv = new AdminUsersPagingVO<>(totalCount, currentPage, sizeOfPage, sizeOfBlock);
			if(totalCount > 0) {
				map.put("startNo", pv.getStartNo()+"");
				map.put("endNo", pv.getEndNo()+"");
				List<UserVO> list = usersBoardDAO.selectUserList(map);
				pv.setList(list);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		//log.info("pv 리턴 : {}",pv);
		return pv;
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

	@Override
	public int selectCount(HashMap<String, String> map) {
		int count = 0;
		try {
			count = usersBoardDAO.selectCount(map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<UserVO> selectUsers() {
		List<UserVO> userList = null;
		try {
			userList = usersBoardDAO.selectUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userList;
	}
}
