package com.choongang.shoppingmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.AddressVO;

@Mapper
public interface AddressMapper {

	// 배송지 추가
	void insertAddress(AddressVO addressVO);
	
	//배송지 목록 확인
	List<AddressVO> findAddressListByUserId(int userId);
	
	void updateAddress(AddressVO address);
    
}