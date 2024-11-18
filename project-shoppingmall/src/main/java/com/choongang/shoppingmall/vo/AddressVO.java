package com.choongang.shoppingmall.vo;

import lombok.Data;

@Data
public class AddressVO {
	
	private int addr_id;	// 배송지 고유번호
	private int userId;	// 회원 고유번호
	private String name;  // 배송지 이름
	private String postcode;  // 우편번호
	private String address;  // 배송지 주소
	private String address_detail;  // 배송지 상세주소
	private String req;  // 배송 요청사항
	
}
