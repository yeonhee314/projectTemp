package com.choongang.shoppingmall.service;

import com.choongang.shoppingmall.vo.QuestionCommentVO;

public interface QuestionCommentService {
	QuestionCommentVO selectCommentById(int question_id);
	// 문의 답변 저장
	void addToQuestion(QuestionCommentVO vo);
	// 문의 답변 수정
	void updateComment(QuestionCommentVO vo);
	// 문의 답변 삭제
	void deleteToQuestion(QuestionCommentVO vo);
}
