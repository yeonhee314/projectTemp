package com.choongang.shoppingmall.service;

import java.util.HashMap;
import java.util.List;

import com.choongang.shoppingmall.vo.AdminProductsPagingVO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.QuestionCommentVO;
import com.choongang.shoppingmall.vo.QuestionVO;

public interface QuestionService {
	// 문의글 총 개수
	int selectQuestionCount(HashMap<String, String> map);
	// 문의 아이디로 가져오기
	QuestionVO selectById(int question_id);
	// 문의 페이징
	PagingVO<QuestionVO> getQuestionList(int currentPage, int sizeOfPage, int sizeOfBlock, String field, String search);
	AdminProductsPagingVO<QuestionVO> getAdminQuestionList(int currentPage, int sizeOfPage, int sizeOfBlock, String field, String search);
	// 문의 내역 저장
	void addToQuestion(QuestionVO vo);
	// 문의 접수상태 변경
	void updateStatus(QuestionVO vo);
	// 문의 내역 삭제
	void deleteToQuestion(QuestionVO vo);
	// 유저 아이디 별 문의 내역 가져오기
	List<QuestionVO> selectQuestionListByUserId(int user_id);

	// 유저 아이디 별 문의 개수
	int selectQuestionCountByUserId(int user_id);

	// 문의 답변 가져오기
	List<QuestionCommentVO> getCommList(int user_id);
	// 답변대기 개수
	int selectCountByStatus();
}
