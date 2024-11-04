package com.choongang.shoppingmall.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.QuestionCommentDAO;
import com.choongang.shoppingmall.vo.QuestionCommentVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("questionCommentService")
public class QuestionCommentServiceImpl implements QuestionCommentService{
	@Autowired
	private QuestionCommentDAO questionCommentDAO;

	@Override
	public void addToQuestion(QuestionCommentVO vo) {
		try {
			questionCommentDAO.addToComment(vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("답변 저장 : " + vo);
	}
	
	@Override
	public void updateComment(QuestionCommentVO vo) {
		try {
			questionCommentDAO.updateComment(vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("답변 수정 : " + vo );
	}

	@Override
	public void deleteToQuestion(QuestionCommentVO vo) {
		try {
			questionCommentDAO.deleteToComment(vo.getQuestion_cmt_id());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("답변 삭제 : " + vo );
	}

	@Override
	public QuestionCommentVO selectCommentById(int question_id) {
		QuestionCommentVO question_commentVO = null;
		try {
			question_commentVO = questionCommentDAO.selectCommentById(question_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return question_commentVO;
	}

}
