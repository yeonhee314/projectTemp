package com.choongang.shoppingmall.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.QuestionVO;

@Mapper
public interface QuestionDAO {
	void addToQuestion(QuestionVO vo) throws SQLException;
}
