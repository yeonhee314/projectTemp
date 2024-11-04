package com.choongang.shoppingmall.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.QuestionCommentVO;

@Mapper
public interface QuestionCommentDAO {
	QuestionCommentVO selectCommentById(int question_id) throws SQLException;
	// 문의 답변 저장
	void addToComment(QuestionCommentVO vo) throws SQLException;
	// 문의 답변 수정
	void updateComment(QuestionCommentVO vo) throws SQLException;
	// 문의 답변 삭제
	void deleteToComment(int question_cmt_id) throws SQLException;
	
}
