package com.choongang.shoppingmall.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.ReviewDAO;
import com.choongang.shoppingmall.vo.AdminProductsPagingVO;
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
				pv.setList(reviewDAO.selectReviewList(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pv;
	}
	
	@Override
	public AdminProductsPagingVO<ReviewVO> selectAdminReviewPage(int currentPage, int sizeOfPage, int sizeOfBlock, String field,
			String search) {
		AdminProductsPagingVO<ReviewVO> pv = null;
		
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("field", field == null || field.trim().length()==0 ? null : field);
			map.put("search", search == null || search.trim().length()==0 ? null : search);
			int totalCount = reviewDAO.selectReviewTotalCount(map);
			pv = new AdminProductsPagingVO<>(totalCount, currentPage, sizeOfPage, sizeOfBlock);
			if(totalCount > 0) {
				map.put("startNo", pv.getStartNo()+"");
				map.put("endNo", pv.getEndNo()+"");
				List<ReviewVO> list = reviewDAO.selectReviewPage(map);
				pv.setList(list);
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

	@Override
	public int selectReviewCountByUserId(int user_id) {
		int count = 0;
			try {
				count = reviewDAO.selectReviewCountByUserId(user_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return count;
	}

	@Override
	public void addToReview(ReviewVO vo) {
		try {
			String projectDir = System.getProperty("user.dir");
			String uploadDir = projectDir + "/src/main/resources/static/images/review-imgs";
			
			File dir = new File(uploadDir);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			if(vo.getUploadfile() != null && !vo.getUploadfile().isEmpty()) {
				if(!dir.exists()) {
					dir.mkdirs();
				}
				String fileName = vo.getUploadfile().getOriginalFilename();
				File file = new File(uploadDir, fileName);
				log.info("파일 저장 경로 : /images/review-imgs/" + fileName);
				vo.getUploadfile().transferTo(file);
				vo.setReview_img("/images/review-imgs/" + fileName);
				
			}else {
				vo.setReview_img("N");
			}
			reviewDAO.addToReview(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<ReviewVO> selectReviewByUserId(int user_id) {
		List<ReviewVO> vo = null;
		try {
			vo = reviewDAO.selectReviewByUserId(user_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public void deleteToReview(int review_id) {
		try {
			reviewDAO.deleteToReview(review_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateToReview(ReviewVO vo) {
		try {
			String projectDir = System.getProperty("user.dir");
			String uploadDir = projectDir + "/src/main/resources/static/images/review-imgs";
			
			File dir = new File(uploadDir);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			if(vo.getUploadfile() != null && !vo.getUploadfile().isEmpty()) {
				if(!dir.exists()) {
					dir.mkdirs();
				}
				String fileName = vo.getUploadfile().getOriginalFilename();
				File file = new File(uploadDir, fileName);
				log.info("파일 저장 경로 : /images/review-imgs/" + fileName);
				vo.getUploadfile().transferTo(file);
				vo.setReview_img("/images/review-imgs/" + fileName);
				
			}else {
				vo.setReview_img("N");
			}
			reviewDAO.updateToReview(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
