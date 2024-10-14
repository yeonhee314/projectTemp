package com.choongang.shoppingmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.choongang.shoppingmall.service.CategoryService;
import com.choongang.shoppingmall.service.ProductService;
import com.choongang.shoppingmall.service.UsersBoardService;
import com.choongang.shoppingmall.vo.AdminUsersPagingVO;
import com.choongang.shoppingmall.vo.CategoryVO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ProductVO;
import com.choongang.shoppingmall.vo.UserPagingVO;
import com.choongang.shoppingmall.vo.UserVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Configuration
@Slf4j
public class AdminController {
	@Autowired
	private UsersBoardService usersBoardService;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;
	// 관리자 페이지 접근
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	// 유저목록 페이징
	@GetMapping(value =  "/admin/users")
	public String adminUsers(@RequestParam (required = false) String field,
						@RequestParam (required = false) String search,
						@ModelAttribute UserPagingVO userPagingVO ,
						Model model) {
		AdminUsersPagingVO<UserVO> pv = usersBoardService.getUserList(userPagingVO.getCurrentPage(), userPagingVO.getSizeOfPage(), userPagingVO.getSizeOfBlock(), userPagingVO.getField(), userPagingVO.getSearch());
		List<UserVO> list= usersBoardService.selectAll();
		model.addAttribute("pv", pv);
		model.addAttribute("upv", userPagingVO);
		model.addAttribute("field", field);
		model.addAttribute("search", search);
		model.addAttribute("list", list);
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "<br>");
		
		return "admin-users";
	}
	/*
	// 전체 회원 목록
	@GetMapping(value =  "/admin/users")
	public String users(Model model) {
		model.addAttribute("list",usersBoardService.selectAll());
		model.addAttribute("count",usersBoardService.selectCount());
		return "admin-users";
	}
	*/
	
	// 회원 상세 조회
	@GetMapping(value = "/admin/user/details")
	public String adminUserDetail(Integer user_id, Model model) {
		model.addAttribute("user_id",user_id);
		model.addAttribute("details", usersBoardService.selectByID(user_id));
		return "admin-userdetails";
	}
	// 상품 목록 페이징
	@GetMapping("/admin/products")
	public String adminProducts(@ModelAttribute UserPagingVO userPagingVO ,Model model) {
		PagingVO<ProductVO> pv = productService.getProductList(userPagingVO.getCurrentPage(), userPagingVO.getSizeOfPage(), userPagingVO.getSizeOfBlock());
		// 카테고리 페이징
		PagingVO<CategoryVO> cv = categoryService.getCategoryList(userPagingVO.getCurrentPage(), userPagingVO.getSizeOfPage(), userPagingVO.getSizeOfBlock());
		model.addAttribute("pv", pv);
		model.addAttribute("cv", cv);
		model.addAttribute("upv", userPagingVO);
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "<br>");
		
		return "admin-products";
	}
	// 문의 관리
	@GetMapping("/admin/qna")
	public String adminQna() {
		return "admin-qna";
	}
}
