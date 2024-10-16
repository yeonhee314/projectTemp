package com.choongang.shoppingmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choongang.shoppingmall.service.CategoryService;
import com.choongang.shoppingmall.service.ProductService;
import com.choongang.shoppingmall.service.UsersBoardService;
import com.choongang.shoppingmall.vo.AdminCategoryPagingVO;
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
		AdminCategoryPagingVO<CategoryVO> cv = categoryService.getCategoryList(userPagingVO.getCurrentPage(), userPagingVO.getSizeOfPage(), userPagingVO.getSizeOfBlock());
		model.addAttribute("pv", pv);
		model.addAttribute("cv", cv);
		model.addAttribute("upv", userPagingVO);
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "<br>");
		model.addAttribute("cvo", new CategoryVO());
		
		return "admin-products";
	}
	
	// 카테고리 중복확인(숫자 1개를 넘긴다. 0이면 사용가능 0이아니면 사용 불가능)
		@GetMapping(value = "/test/categoryCheck", produces = "text/plain;charset=UTF-8")
		@ResponseBody
		public String categoryCheck(@RequestParam(required = false)String category_name) {
			return categoryService.selectCountByCategoryName(category_name)+"";
		}
		
	// Get방식일 경우 상품관리로 보내기
		@GetMapping("/categoryOk")
		public String categoryOkGet() {
			return "redirect:/admin/products";
		}
	// Post전송일때만 저장
		@PostMapping("/categoryOk")
		public String categoryOkPost(@ModelAttribute(value = "vo") CategoryVO vo) {
			categoryService.insert(vo); // 저장
			return "redirect:/admin/products";
		}
		
		@GetMapping("/updateOk")
		public String categoryUpdateOkGet() {
			return "redirect:/admin/products";
		}
		@PostMapping("/updateOk")
		public String categoryUpdateOkPost(@ModelAttribute(value = "vo") CategoryVO vo) {
			
			categoryService.update(vo); // 저장
			return "redirect:/admin/products";
		}
		
		
	// 문의 관리
	@GetMapping("/admin/qna")
	public String adminQna() {
		return "admin-qna";
	}
}
