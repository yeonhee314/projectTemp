package com.choongang.shoppingmall.vo;

import java.util.Date;

import lombok.Data;

@Data
public class QuestionCommentVO {
	
	private int question_cmt_id;	// 문의댓글 번호
	private int question_id;	// 문의글 번호
	private String question_cmt; // 문의댓글
	private Date cmt_regdate;	// 댓글 작성일
}
