package com.choongang.shoppingmall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.choongang.shoppingmall.service.QuestionService;
import com.choongang.shoppingmall.vo.QuestionVO;

@RestController
@RequestMapping("/submitQuestion")
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	
	@PostMapping
	public ResponseEntity<Map<String, String>> submitQuestion(@ModelAttribute QuestionVO questionVO){
		questionService.addToQuestion(questionVO);
		
		Map<String, String> response = new HashMap<>();
		response.put("message", "문의 접수 완료");
		return ResponseEntity.ok(response);
	}
	
}
