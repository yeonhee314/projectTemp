package com.choongang.shoppingmall.vo;

import lombok.Data;

@Data
public class AddressVO {
	
	private int addr_id;	// 배송지 고유번호
	private int userId;	// 회원 고유번호
	private String addr_name;  // 배송지 이름
	private String addr;  // 배송지 주소
	private String addr_detail;  // 배송지 상세주소
	private String addr_code;  // 우편번호
	private String req;  // 배송 요청사항
	
}
