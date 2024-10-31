package com.choongang.shoppingmall.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.choongang.shoppingmall.service.QuestionService;
import com.choongang.shoppingmall.vo.QuestionVO;

@Controller
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
