package com.choongang.shoppingmall.service;

import java.util.HashMap;
import java.util.List;

import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.QuestionVO;

public interface QuestionService {
	int selectQuestionCount(HashMap<String, String> map);
	QuestionVO selectById(int question_id);
	PagingVO<QuestionVO> getQuestionList(int currentPage, int sizeOfPage, int sizeOfBlock, String field, String search);
	
	// 문의 내역 저장
	void addToQuestion(QuestionVO vo);
	// 유저 아이디 별 문의 내역 가져오기
	List<QuestionVO> selectQuestionListByUserId(int user_id);
}
