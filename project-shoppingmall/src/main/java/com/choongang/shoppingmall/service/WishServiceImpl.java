package com.choongang.shoppingmall.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.WishDAO;
import com.choongang.shoppingmall.vo.WishVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("wishService")
public class WishServiceImpl implements WishService{
	@Autowired
	private WishDAO wishDAO;

	@Override
	public void addToWishList(WishVO wishVO) {
		try {
			wishDAO.addToWishList(wishVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
