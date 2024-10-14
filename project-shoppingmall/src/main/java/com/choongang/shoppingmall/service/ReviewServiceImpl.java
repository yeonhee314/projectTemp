package com.choongang.shoppingmall.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.ReviewDAO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ReviewVO;

import lombok.extern.slf4j.Slf4j;

@Service("reviewService")
@Slf4j
public class ReviewServiceImpl implements ReviewService{
	@Autowired
	private ReviewDAO reviewDAO;

	@Override
	public ReviewVO selectReviewId(int id) {
		ReviewVO vo = null;
		try {
			vo = reviewDAO.selectReviewId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public int selectReviewCount(int id) {
		int reviewCount = 0;
		try {
			reviewCount = reviewDAO.selectReviewCount(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reviewCount;
	}

	@Override
	public PagingVO<ReviewVO> getReviewList(int id, int currentPage, int sizeOfPage, int sizeOfBlock) {
		PagingVO<ReviewVO> pv = null;
		
		try {
			int totalCount = reviewDAO.selectReviewCount(id);
			pv = new PagingVO<>(totalCount, currentPage, sizeOfPage, sizeOfBlock);
			if(totalCount > 0) {
				HashMap<String, Integer> map = new HashMap<>();
				map.put("startNo", pv.getStartNo());
				map.put("endNo", pv.getEndNo());
				map.put("product_id", id);
				pv.setList(reviewDAO.selectReviewList(map));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pv;
	}
	
}
