package com.choongang.shoppingmall.service;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.QuestionCommentDAO;
import com.choongang.shoppingmall.dao.QuestionDAO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.QuestionCommentVO;
import com.choongang.shoppingmall.vo.QuestionVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("questionService")
public class QuestionServiceImpl implements QuestionService{
	@Autowired
	private QuestionDAO questionDAO;
	@Autowired
	private QuestionCommentDAO questionCommentDAO;
	
	// 문의 내역 저장
	@Override
	public void addToQuestion(QuestionVO vo) {
		try {
			// 파일 저장 경로 설정
			String projectDir = System.getProperty("user.dir");
	        String uploadDir = projectDir + "/src/main/resources/static/images/customer/";
	        
	        // 디렉토리가 없는 경우 생성
	        File dir = new File(uploadDir);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }
			
	        // 파일 저장
	        if (vo.getUploadfile() != null && !vo.getUploadfile().isEmpty()) {
	            if (!dir.exists()) {
	                dir.mkdirs();
	            }
	            
	            // 파일 저장
	            String fileName = vo.getUploadfile().getOriginalFilename();
	            File file = new File(uploadDir, fileName);
	            log.info("파일 저장 경로 : /images/customer/" + fileName);
	            vo.getUploadfile().transferTo(file); // 파일 저장
	            vo.setQuestion_add("/images/customer/" + fileName);
	        }else {
	        	vo.setQuestion_add("N");
	        }
	        
	        questionDAO.addToQuestion(vo);
	    } catch (Exception e) {
	        e.printStackTrace(); 
	    }
	}

	@Override
	public int selectQuestionCount(HashMap<String, String> map) {
		int count = 0;
		try {
			count = questionDAO.selectQuestionCount(map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public PagingVO<QuestionVO> getQuestionList(int currentPage, int sizeOfPage, int sizeOfBlock, String field,
			String search) {
		PagingVO<QuestionVO> qv = null;
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("field", field == null || field.trim().length()==0 ? null : field);
			map.put("search", search == null || search.trim().length()==0 ? null : search);
			int totalCount = questionDAO.selectQuestionCount(map);
			qv = new PagingVO<>(totalCount, currentPage, sizeOfPage, sizeOfBlock);
			if(totalCount > 0) {
				map.put("startNo", qv.getStartNo()+"");
				map.put("endNo", qv.getEndNo()+"");
				List<QuestionVO> list = questionDAO.selectQuestionList(map);
				qv.setList(list);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		//log.info("pv 리턴 : {}",pv);
		return qv;
	}
	// 문의글 상세
	@Override
	public QuestionVO selectById(int question_id) {
		QuestionVO questionVO = null;
		try {
			questionVO = questionDAO.selectById(question_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questionVO;
	}

	@Override
	public List<QuestionVO> selectQuestionListByUserId(int user_id) {
	List<QuestionVO> questionList = null;
		try {
			questionList = questionDAO.selectQuestionListByUserId(user_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questionList;
	}
	
	@Override
	public List<QuestionCommentVO> getCommList(int user_id) {
		List<QuestionCommentVO> commList = new ArrayList<>();
		List<QuestionVO> questionList = null;
		try {
			questionList = questionDAO.selectQuestionListByUserId(user_id);
			for(int i = 0; i < questionList.size(); i++) {
				QuestionCommentVO commVO = questionCommentDAO.selectCommentById(questionList.get(i).getQuestion_id());
				if(commVO != null) {
					commList.add(commVO);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return commList;
	}

	@Override
	public void deleteToQuestion(QuestionVO vo) {
		try {
			questionDAO.deleteToQuestion(vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void updateStatus(QuestionVO vo) {
		try {
			questionDAO.updateStatus(vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("상태변경 : {}", vo);
	}
}
