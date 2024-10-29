package com.choongang.shoppingmall.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.QuestionDAO;
import com.choongang.shoppingmall.vo.QuestionVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("questionService")
public class QuestionServiceImpl implements QuestionService{
	@Autowired
	private QuestionDAO questionDAO;
	
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
}
