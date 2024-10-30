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
	void addToQuestion(QuestionVO vo) throws SQLException;
}
