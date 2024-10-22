package com.choongang.shoppingmall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileVO {
	private String uuid;	// 이름 중복 피하기
	private String filename;
	private String contentType;
}
