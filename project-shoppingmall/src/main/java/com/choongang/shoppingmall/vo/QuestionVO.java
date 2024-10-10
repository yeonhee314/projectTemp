package com.choongang.shoppingmall.vo;

import java.util.Date;

import lombok.Data;

@Data
public class QuestionVO {
	
	private int question_id;	// 문의글 번호
	private int user_id;		// 회원 고유번호
	private String question_title;	// 문의제목
	private String question_content;	// 문의내용
	private Date question_regdate;	// 문의 작성일
	private String question_add;	// 첨부파일
	private Boolean is_secret;		// 비밀글여부
}
