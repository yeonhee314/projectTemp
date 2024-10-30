package com.choongang.shoppingmall.service;

import java.util.HashMap;

import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.QuestionVO;

public interface QuestionService {
	int selectQuestionCount(HashMap<String, String> map);
	QuestionVO selectById(int question_id);
	PagingVO<QuestionVO> getQuestionList(int currentPage, int sizeOfPage, int sizeOfBlock, String field, String search);
	void addToQuestion(QuestionVO vo);
}
