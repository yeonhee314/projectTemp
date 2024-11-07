package com.choongang.shoppingmall.vo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class QuestionVO {
	
	private int question_id;		// 문의글 번호
	private int user_id;			// 회원 고유번호
	private String question_title;	// 문의제목
	private String question_content;// 문의내용
	private Date question_regdate;	// 문의 작성일
	private String question_add;	// 첨부파일
	private String inquiryType;		// 문의 유형 - '취소문의', '배송문의', '반품문의', '교환문의', '환불문의', 'AS문의', '오류문의', '기타'
	private String alertEmail;		// 알림을 받을 이메일 주소
	private String question_status;	// 접수 상태 -'답변대기(기본)', '답변완료', '답변거부'
	
	private MultipartFile uploadfile;// 업로드할 파일
	
	private String username;
}
