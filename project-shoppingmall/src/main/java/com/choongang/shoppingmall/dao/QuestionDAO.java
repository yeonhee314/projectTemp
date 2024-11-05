package com.choongang.shoppingmall.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.QuestionVO;

@Mapper
public interface QuestionDAO {
	// 문의글 총 개수
	int selectQuestionCount(HashMap<String, String> map) throws SQLException;
	// 문의 글번호로 가져오기
	QuestionVO selectById(int question_id) throws SQLException;
	// 문의 글 페이징
	List<QuestionVO> selectQuestionList(HashMap<String, String> map) throws SQLException;
	// 문의 내역 저장
	void addToQuestion(QuestionVO vo) throws SQLException;
	// 문의 접수 상태 변경
	void updateStatus(QuestionVO vo) throws SQLException;
	// 문의 내역 삭제
	void deleteToQuestion(QuestionVO vo) throws SQLException;
	// 유저 아이디 별 문의 내역 가져오기
	List<QuestionVO> selectQuestionListByUserId(int user_id) throws SQLException;
	// 유저 아이디 별 문의글 개수
	int selectQuestionCountByUserId(int user_id) throws SQLException;
}
