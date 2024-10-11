package com.choongang.shoppingmall.service;

import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.UsersBoardDAO;
import com.choongang.shoppingmall.vo.PagingVO;
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
	
	
	@Override
	public PagingVO<UserVO> getList(int currentPage, int sizeOfPage, int sizeOfBlock) {
		log.info("getList 인수 : {},{},{}",currentPage,sizeOfPage,sizeOfBlock);
		PagingVO<UserVO> pv = null;
		try {
			int totalCount = usersBoardDAO.selectCount();
			pv = new PagingVO<>(totalCount, currentPage, sizeOfPage, sizeOfBlock); // 계산완료
			if(totalCount>0) {
				HashMap<String, Integer> map = new HashMap<>();
				map.put("startNo", pv.getStartNo());
				map.put("endNo", pv.getEndNo());
				pv.setList(usersBoardDAO.selectAll(map));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		log.info("getList 결과 : {} ",pv);
		return pv;
	}
	
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
}
