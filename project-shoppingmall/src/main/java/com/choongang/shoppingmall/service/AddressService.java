package com.choongang.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.AddressMapper;
import com.choongang.shoppingmall.vo.AddressVO;
import com.choongang.shoppingmall.vo.CartVO;

@Service
public class AddressService {

	@Autowired
	private AddressMapper addressMapper;
	
	public void addAddress(int userId, String addr_name, String addr, String addr_detail,String addr_code) {
		AddressVO addressVO = new AddressVO();
		addressVO.setUserId(userId);
		addressVO.setAddr_name(addr_name);
		addressVO.setAddr(addr);
		addressVO.setAddr_detail(addr_detail);
		addressVO.setAddr_code(addr_code);
	
		addressMapper.insertAddress(addressVO);
	}
	
	public List<AddressVO> getAddressList(int userId){
		return addressMapper.findAddressListByUserId(userId);
	}
	
	public void updateAddress(AddressVO addressVO) {
		addressMapper.updateAddress(addressVO);
	}
}