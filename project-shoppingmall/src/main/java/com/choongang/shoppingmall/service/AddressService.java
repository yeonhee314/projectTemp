package com.choongang.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choongang.shoppingmall.dao.AddressMapper;
import com.choongang.shoppingmall.vo.AddressVO;
import com.choongang.shoppingmall.vo.CartVO;

@Service
public class AddressService {

	@Autowired
	private AddressMapper addressMapper;
	
    public void addAddress(int userId, int addr_id, String name, String address, String address_detail,String postcode) {
        AddressVO addressVO = new AddressVO();
        addressVO.setUserId(userId);
        addressVO.setAddr_id(addr_id);
        addressVO.setName(name);
        addressVO.setAddress(address);
        addressVO.setAddress_detail(address_detail);
        addressVO.setPostcode(postcode);
		addressMapper.insertAddress(addressVO);
	}
	
	public List<AddressVO> getAddressList(int userId){
		return addressMapper.findAddressListByUserId(userId);
	}
	
	public AddressVO getAddressByAddrId(int addr_id) {
        return addressMapper.selectAddressByAddrId(addr_id);
    }
 
	public void updateAddress(AddressVO addressVO) {
        addressMapper.updateAddress(addressVO);
    }
	
	public void deleteAddress(int addr_id) {
		addressMapper.deleteAddress(addr_id);
	}
	
	 @Transactional
	    public void updateUserAddress(int userId, int addr_id,String name, String postcode, String address, String address_detail){
	        // 선택한 배송지 주소 가져오기
	        AddressVO addressVO = addressMapper.selectAddressByAddrId(addr_id);
	        if (addressVO != null) {
	            // 유저 주소 업데이트
	        	addressMapper.updateUserAddress(userId, addr_id,addressVO.getName(),addressVO.getPostcode() ,addressVO.getAddress(), addressVO.getAddress_detail());
	        }
	    }
	
}