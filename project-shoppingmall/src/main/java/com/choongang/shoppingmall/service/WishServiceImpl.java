package com.choongang.shoppingmall.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.WishDAO;
import com.choongang.shoppingmall.vo.UserVO;
import com.choongang.shoppingmall.vo.WishVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("wishService")
public class WishServiceImpl implements WishService{
	@Autowired
	private WishDAO wishDAO;

	// 저장
	@Override
	public void addToWishList(WishVO wishVO) {
		try {
			wishDAO.addToWishList(wishVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 삭제
	@Override
	public void deleteToWishList(WishVO wishVO) {
		try {
			wishDAO.deleteToWishList(wishVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 유저별 위시리스트 불러오기
	@Override
	public List<WishVO> selectWishByUserId(int user_id) {
		List<WishVO> list = null;
		try {
			list = wishDAO.selectWishByUserId(user_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int isWishCount(int user_id, int product_id) {
		boolean isWish= false;
		List<WishVO> list = null;
		try {
			list = selectWishByUserId(user_id);
			if(list.isEmpty()) return 0;
			HashMap<String, Integer> map = new HashMap<>();
			map.put("user_id", user_id);
			map.put("product_id", product_id);
			
			if(map.get("product_id") == product_id) {
				isWish = wishDAO.isWishCount(map) > 0;
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isWish ? 1 : 0;
	}

}
