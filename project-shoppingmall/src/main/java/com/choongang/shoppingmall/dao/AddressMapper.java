package com.choongang.shoppingmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.choongang.shoppingmall.vo.AddressVO;

@Mapper
public interface AddressMapper {

	// 배송지 추가
	void insertAddress(AddressVO addressVO);
	
	//배송지 목록 확인
	List<AddressVO> findAddressListByUserId(int userId);
	
	//배송지 목록(수정) 확인
	AddressVO selectAddressByAddrId(@Param("addr_id") int addr_id);
	
	//배송지 수정 
	void updateAddress(AddressVO addressVO);
	
	//배송지 삭제
	int deleteAddress(int addr_id);
	
	//기본 배송지 변경
	void updateUserAddress(@Param("userId") int userId, 
						   @Param("addr_id")int addr_id, 
						   @Param("name") String name,
						   @Param("postcode") String postcode, 
						   @Param("address") String address, 
						   @Param("address_detail") String address_deteail  
						   );



    
}