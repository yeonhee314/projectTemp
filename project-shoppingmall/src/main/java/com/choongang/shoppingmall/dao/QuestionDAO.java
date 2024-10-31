package com.choongang.shoppingmall.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.QuestionVO;

@Mapper
public interface QuestionDAO {
	int selectQuestionCount(HashMap<String, String> map) throws SQLException;
	QuestionVO selectById(int question_id) throws SQLException;
	List<QuestionVO> selectQuestionList(HashMap<String, String> map) throws SQLException;
	// 문의 내역 저장
	void addToQuestion(QuestionVO vo) throws SQLException;
	// 문의 내역 삭제
	void deleteToQuestion(QuestionVO vo) throws SQLException;
	// 유저 아이디 별 문의 내역 가져오기
	List<QuestionVO> selectQuestionListByUserId(int user_id) throws SQLException;
}
