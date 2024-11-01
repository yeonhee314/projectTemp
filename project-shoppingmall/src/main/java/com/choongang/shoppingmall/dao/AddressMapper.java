package com.choongang.shoppingmall.dao;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.AddressVO;

@Mapper
public interface AddressMapper {

	// 배송지 추가
	void insertAddress(AddressVO addressVO);

	Object findById(int userId);
    
}