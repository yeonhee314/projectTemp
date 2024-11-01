package com.choongang.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.AddressMapper;
import com.choongang.shoppingmall.vo.AddressVO;

@Service
public class AddressService {

	@Autowired
	private AddressMapper addressMapper;
	
	public void addAddress(AddressVO addressVO, Integer userId) {
		addressVO.setUser_id(userId);
		addressMapper.insertAddress(addressVO);
	}


}