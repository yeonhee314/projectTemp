package com.choongang.shoppingmall.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.choongang.shoppingmall.service.CategoryService;
import com.choongang.shoppingmall.service.ProductService;
import com.choongang.shoppingmall.service.QuestionCommentService;
import com.choongang.shoppingmall.service.QuestionService;
import com.choongang.shoppingmall.service.ReviewService;
import com.choongang.shoppingmall.service.UsersBoardService;
import com.choongang.shoppingmall.vo.AdminCategoryPagingVO;
import com.choongang.shoppingmall.vo.AdminUsersPagingVO;
import com.choongang.shoppingmall.vo.CategoryVO;
import com.choongang.shoppingmall.vo.FileVO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ProductPagingVO;
import com.choongang.shoppingmall.vo.ProductVO;
import com.choongang.shoppingmall.vo.QuestionCommentVO;
import com.choongang.shoppingmall.vo.QuestionVO;
import com.choongang.shoppingmall.vo.ReviewVO;
import com.choongang.shoppingmall.vo.UserPagingVO;
import com.choongang.shoppingmall.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
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
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionCommentService questionCommentService;
	@Autowired
	private ReviewService reviewService;
	
	ResourceLoader resourceLoader;
	
	
	// 관리자 페이지 접근
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	// 유저목록 페이징
	@GetMapping(value =  "/admin/users")
	public String adminUsers(@RequestParam (required = false, name = "field") String field,
						@RequestParam (required = false, name = "search") String search,
						@ModelAttribute UserPagingVO userPagingVO ,
						Model model) {
		AdminUsersPagingVO<UserVO> pv = usersBoardService.getUserList(userPagingVO.getCurrentPage(), userPagingVO.getSizeOfPage(), userPagingVO.getSizeOfBlock(), field, search);
		model.addAttribute("pv", pv);
		model.addAttribute("upv", userPagingVO);
		model.addAttribute("field", field);
		model.addAttribute("search", search);
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "<br>");
		return "admin-users";
	}
	
	// 회원 상세 조회
	@GetMapping(value = "/admin/user/details")
	public String adminUserDetail(Integer user_id, Model model) {
		model.addAttribute("user_id",user_id);
		model.addAttribute("details", usersBoardService.selectByID(user_id));
		model.addAttribute("qnaCount", questionService.selectQuestionCountByUserId(user_id));
		return "admin-userdetails";
	}
	// 상품 관리
	@GetMapping("/admin/products")
	public String adminProducts(
			@RequestParam (required = false, name = "field") String field,
			@RequestParam (required = false, name = "search") String search,
			@ModelAttribute ProductPagingVO productPagingVO,
			@ModelAttribute CategoryVO categoryVO,
			@ModelAttribute ProductVO productVO
			, Model model) {
		PagingVO<ProductVO> pv = productService.getProductList(productPagingVO.getCurrentPage(), productPagingVO.getSizeOfPage(), productPagingVO.getSizeOfBlock(), field, search);
		// 카테고리 페이징
		AdminCategoryPagingVO<CategoryVO> cv = categoryService.getCategoryList(productPagingVO.getCurrentPage(), productPagingVO.getSizeOfPage(), productPagingVO.getSizeOfBlock());
		model.addAttribute("pv", pv);
		model.addAttribute("cv", cv);
		model.addAttribute("ppv", productPagingVO);
		model.addAttribute("field", field);
		model.addAttribute("search", search);
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "<br>");
		return "admin-products";
	}
	
	// 카테고리 중복확인(숫자 1개를 넘긴다. 0이면 사용가능 0이아니면 사용 불가능)
		@GetMapping(value = "/test/categoryCheck", produces = "text/plain;charset=UTF-8")
		@ResponseBody
		public String categoryCheck(@RequestParam(required = false, name = "category_name")String category_name) {
			return categoryService.selectCountByCategoryName(category_name)+"";
		}
		
	// Get방식일 경우 상품관리로 보내기
		@GetMapping("/categoryOk")
		public String categoryOkGet() {
			return "redirect:/admin/products";
		}
	// Post전송일때만 저장
		@PostMapping("/categoryOk")
		public String categoryOkPost(@ModelAttribute(value = "vo") CategoryVO vo,
				Model model) {
			int count = categoryService.selectCountByCategoryName(vo.getCategory_name());
			if (count > 0) {
				 model.addAttribute("msg","카테고리명 중복");
				 model.addAttribute("url","/admin/products");
				return "alert";
			}
			categoryService.insert(vo);
			return "redirect:/admin/products";
		}
		
		@GetMapping("/updateOk")
		public String categoryUpdateOkGet() {
			return "redirect:/admin/products";
		}
		@PostMapping("/updateOk")
		public String categoryUpdateOkPost(@ModelAttribute(value = "vo") CategoryVO vo, Model model) {
			int count = categoryService.selectCountByCategoryName(vo.getCategory_name());
			if (count > 0) {
				model.addAttribute("msg","카테고리명 중복");
				model.addAttribute("url","/admin/products");
				return "alert";
			}
			categoryService.update(vo);
			return "redirect:/admin/products";
		}
		
		@GetMapping("/deleteOk")
		public String categoryDeleteOkGet() {
			return "redirect:/admin/products";
		}
		@PostMapping("/deleteOk")
		public String categoryDeleteOkPost(@ModelAttribute(value = "vo") CategoryVO vo) {
			categoryService.deleteById(vo);
			return "redirect:/admin/products";
		}
		// 상품 등록 페이지
		@GetMapping("/admin/products/form")
		public String productForm(Model model) {
			List<CategoryVO> cglist = categoryService.selectCategory();
			model.addAttribute("cglist", cglist);
			return "admin-product-form";
		}
		// 상품명 중복확인
		@GetMapping(value = "/test/productNameCheck", produces = "text/plain;charset=UTF-8")
		@ResponseBody
		public String productNameCheck(@RequestParam(required = false, name = "product_name")String product_name) {
			return productService.selectCountByProductName(product_name)+"";
		}
		// 상품등록 처리
		@GetMapping("/pdAddOk")
		public String pdAddOkGet() {
			return "redirect:/admin/products";
		}
		@PostMapping("/pdAddOk")
		public String pdAddOkPost(@RequestParam(required = false, name = "content") String content,
				@RequestParam(required = false, name = "uploadFile") MultipartFile[] uploadFile,
				@ModelAttribute(value = "vo") ProductVO productVO,
				@ModelAttribute CategoryVO vo, Model model, HttpServletRequest request) throws IOException{
			int count = productService.selectCountByProductName(productVO.getProduct_name());
			if (count > 0) {
				model.addAttribute("msg","상품명 중복");
				model.addAttribute("url","/admin/products/form");
				return "alert";
			}
			productVO.setImg_count(uploadFile.length);
			productService.insert(productVO);
			String filePath = request.getSession().getServletContext().getRealPath("/images");
			File file = new File(filePath); 
			if(!file.exists()) file.mkdirs();
			
			// 파일 처리
			List<FileVO> list = new ArrayList<>();
			if(uploadFile!=null && uploadFile.length>0) {
				for(MultipartFile f : uploadFile) {
					if(!f.isEmpty()) { 
						// 현재는 미사용
						FileVO fvo = new FileVO(
										UUID.randomUUID().toString(), 
										f.getOriginalFilename(),
										f.getContentType());
						list.add(fvo);
						// 파일명 중복처리
						String filename = "product-"+ productVO.getProduct_id()+"-"+ "1.jpg";
						File newFile = new File(filePath, filename);
						if(newFile.exists()) {
							for(int i=2;; i++) {
								int dot = filename.lastIndexOf("1");
								String front = filename.substring(0, dot);
								String end = filename.substring(dot+1);
								String newFileName = front + i + end;
								newFile = new File(filePath, newFileName);
								if(!newFile.exists()) {
									filename = newFileName;
									break;
								}
							}
						}
						f.transferTo(newFile); 
						log.info("=".repeat(100));
						log.info("저장 :" + newFile);
						log.info("=".repeat(100));
					}
				}
			}
			model.addAttribute("content", content);
			model.addAttribute("list", list);
			return "redirect:/admin/products";
		}
	// 상품 정보 수정
	@GetMapping("/admin/products/edit")
	public String adminProductEdit(Model model, int product_id) {
		List<CategoryVO> cglist = categoryService.selectCategory();
		ProductVO pd = productService.selectByProductId(product_id);
		model.addAttribute("cglist", cglist);
		model.addAttribute("pd", pd);
		return "admin-product-edit";
	}
	@GetMapping("/pdUpdateOk")
	public String pdUpdateOkGet() {
		return "redirect:/admin/products";
	}
	@PostMapping("/pdUpdateOk")
	public String pdUpdateOkPost(@RequestParam(required = false, name = "content") String content,
			@RequestParam(required = false, name = "uploadFile") MultipartFile[] uploadFile,
			@ModelAttribute(value = "vo") ProductVO productVO,
			@ModelAttribute CategoryVO vo, Model model, HttpServletRequest request) throws IOException{
		//productVO.setImg_count(uploadFile.length);
		
		productService.update(productVO);
		String filePath = request.getSession().getServletContext().getRealPath("/images");
		File file = new File(filePath); 
		if(!file.exists()) file.mkdirs();
		
		// 파일 처리
		List<FileVO> list = new ArrayList<>();
		if(uploadFile!=null && uploadFile.length>0) {
			for(MultipartFile f : uploadFile) {
				if(!f.isEmpty()) { 
					// 현재는 미사용
					FileVO fvo = new FileVO(
									UUID.randomUUID().toString(), 
									f.getOriginalFilename(),
									f.getContentType());
					list.add(fvo);
					// 파일명 중복처리
					String filename = "product-"+ productVO.getProduct_id()+"-"+ "1.jpg";
					File newFile = new File(filePath, filename);
					if(newFile.exists()) {
						for(int i=2;; i++) {
							int dot = filename.lastIndexOf("1");
							String front = filename.substring(0, dot);
							String end = filename.substring(dot+1);
							String newFileName = front + i + end;
							newFile = new File(filePath, newFileName);
							if(!newFile.exists()) {
								filename = newFileName;
								break;
							}
						}
					}
					f.transferTo(newFile); 
					log.info("=".repeat(100));
					log.info("저장 :" + newFile);
					log.info("=".repeat(100));
				}
			}
		}
		model.addAttribute("content", content);
		model.addAttribute("list", list);
		return "redirect:/admin/products";
	}
	@GetMapping("/pdDeleteOk")
	public String pdDeleteOkGet() {
		return "redirect:/admin/products";
	}
	@PostMapping("/pdDeleteOk")
	public String pdDeleteOkPost(@ModelAttribute ProductVO productVO) {
		productService.delete(productVO.getProduct_id());
		
		return "redirect:/admin/products";
	}
	// 문의 관리
	@GetMapping("/admin/qna")
	public String adminQna(@RequestParam (required = false, name = "field") String field,
			@RequestParam (required = false, name = "search") String search,
			@ModelAttribute ProductPagingVO productPagingVO ,
			Model model) {
		PagingVO<QuestionVO> qv = questionService.getQuestionList(productPagingVO.getCurrentPage(), productPagingVO.getSizeOfPage(), productPagingVO.getSizeOfBlock(), field, search);
		model.addAttribute("qv", qv);
		model.addAttribute("ppv", productPagingVO);
		model.addAttribute("field", field);
		model.addAttribute("search", search);
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "<br>");
		return "admin-qna";
	}
	// 문의글 상세
	@GetMapping("/admin/qna/view")
	public String adminQnaView(@ModelAttribute QuestionVO questionVO,
			@ModelAttribute UserVO userVO,
			int question_id, Model model) {
		QuestionVO qna = questionService.selectById(question_id);
		model.addAttribute("question_id", question_id);
		model.addAttribute("qna", qna);
		model.addAttribute("user",usersBoardService.selectByID(qna.getUser_id()));
		model.addAttribute("cmt",questionCommentService.selectCommentById(qna.getQuestion_id()));
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "<br>");
		return "admin-qna-view";
	}
	@GetMapping("/adminQnaOk")
	public String adminQnaOkGet() {
		return "redirect:/admin/qna";
	}
	// 문의 답변 저장
	@PostMapping("/adminQnaOk")
	public String adminQnaOkPost(@ModelAttribute(value = "vo") QuestionCommentVO vo,
			@ModelAttribute QuestionVO questionVO) {
		questionCommentService.addToQuestion(vo);
		questionService.updateStatus(questionVO);
		return "redirect:/admin/qna";
	}
	@GetMapping("/adminQnaUpdateOk")
	public String aadminQnaUpdateOkGet() {
		return "redirect:/admin/qna";
	}
	// 문의 답변 수정
	@PostMapping("/adminQnaUpdateOk")
	public String adminQnaUpdateOkPost(@ModelAttribute(value = "vo") QuestionCommentVO vo,
			@ModelAttribute QuestionVO questionVO,Model model) {
		questionCommentService.updateComment(vo);
		questionService.updateStatus(questionVO);
		return "redirect:/admin/qna";
	}
	@GetMapping("/adminQnaDeleteOk")
	public String adminQnaDeleteOkGet() {
		return "redirect:/admin/qna";
	}
	// 문의 답변 삭제
	@PostMapping("/adminQnaDeleteOk")
	public String adminQnaDeleteOkPost(@ModelAttribute QuestionCommentVO vo,
			@ModelAttribute QuestionVO questionVO) {
		questionCommentService.deleteToQuestion(vo);
		questionService.updateStatus(questionVO);
		return "redirect:/admin/qna";
	}
	// 후기 관리
		@GetMapping("/admin/review")
		public String adminReview(@RequestParam (required = false, name = "field") String field,
				@RequestParam (required = false, name = "search") String search,
				@ModelAttribute ProductPagingVO ppv ,
				Model model) {
			PagingVO<ReviewVO> rv = reviewService.selectReviewPage(ppv.getCurrentPage(), ppv.getSizeOfPage(), ppv.getSizeOfBlock(), field, search);
			model.addAttribute("ppv", ppv);
			model.addAttribute("rv", rv);
			model.addAttribute("field", field);
			model.addAttribute("search", search);
			model.addAttribute("newLine", "\n");
			model.addAttribute("br", "<br>");
			return "admin-review";
		}
		// 후기 상세
		@GetMapping("/admin/review/view")
		public String adminReViewView(@ModelAttribute ReviewVO reviewVO,
				@ModelAttribute ProductVO productVO,
				int review_id, Model model) {
			ReviewVO rv = reviewService.selectReviewByReviewId(review_id);
			model.addAttribute("review_id", review_id);
			model.addAttribute("reviewVO", reviewVO);
			model.addAttribute("rv", reviewService.selectReviewByReviewId(review_id));
			model.addAttribute("pdname", productService.selectProductNameById(rv.getCategory_id()));
			model.addAttribute("user", usersBoardService.selectByID(rv.getUser_id()));
			model.addAttribute("newLine", "\n");
			model.addAttribute("br", "<br>");
			return "admin-review-view";
		}
}
