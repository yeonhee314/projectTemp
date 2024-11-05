package com.choongang.shoppingmall.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.ReviewDAO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ReviewVO;
import com.choongang.shoppingmall.vo.UserVO;

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

	@Override
	public double selectRating(int id) {
		double avg = 0.;
		
		try {
			avg = selectReviewCount(id) != 0 ? reviewDAO.selectRating(id) : 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return avg;
	}

	@Override
	public UserVO selectUserId(int id) {
		UserVO vo = null;
		try {
			vo = reviewDAO.selectUserId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	@Override
	public int selectReviewTotalCount(HashMap<String, String> map) {
		int count = 0;
			try {
				count = reviewDAO.selectReviewTotalCount(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
		}

	@Override
	public PagingVO<ReviewVO> selectReviewPage(int currentPage, int sizeOfPage, int sizeOfBlock, String field,
			String search) {
		PagingVO<ReviewVO> rv = null;
		try {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("field", field == null || field.trim().length()==0 ? null : field);
		map.put("search", search == null || search.trim().length()==0 ? null : search);
			int totalCount = reviewDAO.selectReviewTotalCount(map);
		rv = new PagingVO<>(totalCount, currentPage, sizeOfPage, sizeOfBlock);
		if(totalCount > 0) {
			map.put("startNo", rv.getStartNo()+"");
			map.put("endNo", rv.getEndNo()+"");
			List<ReviewVO> list = reviewDAO.selectReviewPage(map);
			rv.setList(list);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rv;
	}

	@Override
	public ReviewVO selectReviewByReviewId(int review_id) {
		ReviewVO rv = null;
		try {
			rv = reviewDAO.selectReviewByReviewId(review_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rv;
	}
}
