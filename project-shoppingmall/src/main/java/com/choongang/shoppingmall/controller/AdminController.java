package com.choongang.shoppingmall.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.choongang.shoppingmall.service.CategoryService;
import com.choongang.shoppingmall.service.OrderService;
import com.choongang.shoppingmall.service.ProductService;
import com.choongang.shoppingmall.service.QuestionCommentService;
import com.choongang.shoppingmall.service.QuestionService;
import com.choongang.shoppingmall.service.ReviewService;
import com.choongang.shoppingmall.service.UserService;
import com.choongang.shoppingmall.service.UsersBoardService;
import com.choongang.shoppingmall.vo.AdminCategoryPagingVO;
import com.choongang.shoppingmall.vo.AdminOrderPagingVO;
import com.choongang.shoppingmall.vo.AdminProductsPagingVO;
import com.choongang.shoppingmall.vo.AdminUsersPagingVO;
import com.choongang.shoppingmall.vo.CategoryVO;
import com.choongang.shoppingmall.vo.FileVO;
import com.choongang.shoppingmall.vo.Order_ItemVO;
import com.choongang.shoppingmall.vo.OrdersVO;
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
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
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
		model.addAttribute("reviewCount", reviewService.selectReviewCountByUserId(user_id));
		model.addAttribute("olist", orderService.getOrdersByUserId(user_id));
		return "admin-userdetails";
	}
	@GetMapping("/pointOk")
	public String userPointOkGet() {
		return "redirect:/admin/users";
	}
	// 회원 포인트 증감
	@PostMapping("/pointOk")
	public String userPointOkPost(@RequestParam (required = false, name = "pnm") String pnm,
			@ModelAttribute UserVO userVO, Model model) {
		model.addAttribute("pnm",pnm);
		userService.pointUpdate(userVO);
		return "redirect:/admin/user/details?user_id="+userVO.getUser_id();
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
		AdminProductsPagingVO<ProductVO> pv = productService.getAdminProductList(productPagingVO.getCurrentPage(), productPagingVO.getSizeOfPage(), productPagingVO.getSizeOfBlock(), field, search);
		// 카테고리 페이징
		AdminCategoryPagingVO<CategoryVO> cv = categoryService.getCategoryList(productPagingVO.getCurrentPage(), productPagingVO.getSizeOfPage(), productPagingVO.getSizeOfBlock());
		model.addAttribute("pv", pv);
		model.addAttribute("cv", cv);
		model.addAttribute("ppv", productPagingVO);
		model.addAttribute("yCount", productService.selectYCount());
		model.addAttribute("nCount", productService.selectNCount());
		model.addAttribute("soldout", productService.selectSoldOutCount());
		model.addAttribute("field", field);
		model.addAttribute("search", search);
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "<br>");
		return "admin-products";
	}
	@GetMapping("/pdStatusOk")
	public String pdStatusOkGet() {
		return "redirect:/admin/products";
	}
	// 상품 판매상태 변경
	@PostMapping("/pdStatusOk")
	public String pdStatusOkPost(@ModelAttribute ProductVO productVO, Model model) {
		productService.updateStatus(productVO);
		return "redirect:/admin/products";
	}
	// 상품 재고 변경
	@PostMapping("/pdStock")
	public String pdStockUpdate(@RequestParam (required = false, name = "sc") String sc,
			@ModelAttribute ProductVO productVO, Model model) {
		model.addAttribute("sc", sc);
		productService.updateStock(productVO);
		return "redirect:/admin/products";
	}
	// 카테고리 중복확인(숫자 1개를 넘긴다. 0이면 사용가능 0이아니면 사용 불가능)
		@GetMapping(value = "/test/categoryCheck", produces = "text/plain;charset=UTF-8")
		@ResponseBody
		public String categoryCheck(@RequestParam(required = false, name = "category_name")String category_name) {
			return categoryService.selectCountByCategoryName(category_name)+"";
		}
		@GetMapping("/categoryOk")
		public String categoryOkGet() {
			return "redirect:/admin/products";
		}
		// 카테고리 저장
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
		@GetMapping("/pdAddOk")
		public String pdAddOkGet() {
			return "redirect:/admin/products";
		}
		// 상품등록
		@PostMapping("/pdAddOk")
		public String pdAddOkPost(
				@RequestParam(required = false, name = "upFile") MultipartFile[] upFile,
				@ModelAttribute(value = "vo") ProductVO productVO,
				@ModelAttribute CategoryVO vo, Model model, HttpServletRequest request) throws IOException{
			int count = productService.selectCountByProductName(productVO.getProduct_name());
			if (count > 0) {
				model.addAttribute("msg","상품명 중복");
				model.addAttribute("url","/admin/products/form");
				return "alert";
			}
			if(!upFile[0].isEmpty()) {
				productVO.setImg_count(upFile.length);
			}else {
				productVO.setImg_count(0);
			}
			productService.insert(productVO);
			String projectDir = System.getProperty("user.dir");
			String filePath = projectDir + "/src/main/resources/static/images/products/product"+productVO.getProduct_id();
			File file = new File(filePath); 
			if(!file.exists()) file.mkdirs();
			// 파일 처리
			List<FileVO> list = new ArrayList<>();
			if(upFile!=null && upFile.length>0) {
				for(MultipartFile f : upFile) {
					if(!f.isEmpty()) { 
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
			model.addAttribute("list", list);
			return "redirect:/admin/products";
		}
	// 상품 정보 수정 페이지
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
	// 상품 수정
	@PostMapping("/pdUpdateOk")
	public String pdUpdateOkPost(
			@RequestParam(required = false, name = "upFile") MultipartFile[] upFile,
			@ModelAttribute(value = "vo") ProductVO productVO,
			@ModelAttribute CategoryVO vo, Model model, HttpServletRequest request) throws IOException{
		if(!upFile[0].isEmpty()) {
			productVO.setImg_count(upFile.length + productVO.getImg_count());
		}
		productService.update(productVO);
		String projectDir = System.getProperty("user.dir");
		String filePath = projectDir + "/src/main/resources/static/images/products/product"+productVO.getProduct_id();
		File file = new File(filePath); 
		if(!file.exists()) file.mkdirs();
		// 파일 처리
		List<FileVO> list = new ArrayList<>();
		if(upFile!=null && upFile.length>0) {
			for(MultipartFile f : upFile) {
				if(!f.isEmpty()) { 
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
		model.addAttribute("list", list);
		return "redirect:/admin/products";
	}
	@GetMapping("/pdDeleteOk")
	public String pdDeleteOkGet() {
		return "redirect:/admin/products";
	}
	// 상품 삭제
	@PostMapping("/pdDeleteOk")
	public String pdDeleteOkPost(@ModelAttribute ProductVO productVO) {
		productService.delete(productVO.getProduct_id());
		String projectDir = System.getProperty("user.dir");
		String filePath = projectDir + "/src/main/resources/static/images/products/product"+productVO.getProduct_id();
		File file = new File(filePath);
		if(file.exists()) {
			File[] files = file.listFiles();
			for (int i=0; i<files.length; i++) {
				files[i].delete();
			}
			file.delete();
		}
		return "redirect:/admin/products";
	}
	// 문의 관리
	@GetMapping("/admin/qna")
	public String adminQna(@RequestParam (required = false, name = "field") String field,
			@RequestParam (required = false, name = "search") String search,
			@ModelAttribute ProductPagingVO productPagingVO ,
			Model model) {
		AdminProductsPagingVO<QuestionVO> qv = questionService.getAdminQuestionList(productPagingVO.getCurrentPage(), productPagingVO.getSizeOfPage(), productPagingVO.getSizeOfBlock(), field, search);
		model.addAttribute("qv", qv);
		model.addAttribute("ppv", productPagingVO);
		model.addAttribute("noreply", questionService.selectCountByStatus());
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
		return "redirect:/admin/qna/view?question_id="+questionVO.getQuestion_id();
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
		return "redirect:/admin/qna/view?question_id="+questionVO.getQuestion_id();
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
			AdminProductsPagingVO<ReviewVO> rv = reviewService.selectAdminReviewPage(ppv.getCurrentPage(), ppv.getSizeOfPage(), ppv.getSizeOfBlock(), field, search);
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
			model.addAttribute("pdname", productService.selectProductNameById(rv.getProduct_id()));
			model.addAttribute("user", usersBoardService.selectByID(rv.getUser_id()));
			model.addAttribute("newLine", "\n");
			model.addAttribute("br", "<br>");
			return "admin-review-view";
		}
		// 주문 관리
		@GetMapping("/admin/orders")
		public String adminOrders(@RequestParam (required = false, name = "field") String field,
				@RequestParam (required = false, name = "search") String search,
				@ModelAttribute ProductPagingVO ppv ,
				Model model) {
			AdminOrderPagingVO<OrdersVO> ov = orderService.selectAdminOrderList(ppv.getCurrentPage(), ppv.getSizeOfPage(), ppv.getSizeOfBlock(), field, search);
			model.addAttribute("ppv", ppv);
			model.addAttribute("ov", ov);
			model.addAttribute("field", field);
			model.addAttribute("search", search);
			model.addAttribute("newLine", "\n");
			model.addAttribute("br", "<br>");
			return "admin-orders";
		}
		// 주문 상세 페이지
		@GetMapping("/admin/orders/view")
		public String adminOrdersView(@ModelAttribute OrdersVO ordersVO,
				@ModelAttribute UserVO userVO,
				@ModelAttribute Order_ItemVO order_ItemVO ,
				int order_id,int user_id, Model model) {
			OrdersVO ov = orderService.selectOrderById(order_id);
			UserVO uv = userService.selectUserById(user_id);
			List<Order_ItemVO> iv = orderService.selectOrderItemByOrderId(order_id);
			Order_ItemVO oiv = orderService.paymentPriceInfo(order_id);
			model.addAttribute("ov", ov);
			model.addAttribute("oiv", oiv);
			model.addAttribute("uv", uv);
			model.addAttribute("iv", iv);
			return "admin-orders-view";
		}
		@GetMapping("/orderShipping")
		public String adminOrderShippingGet() {			
			return "redirect:/admin/orders";
		}
		// 배송중 처리
		@PostMapping("/orderShipping")
		public String adminOrderShippingPost(@ModelAttribute OrdersVO ordersVO,Integer order_id, Integer user_id) {
			orderService.orderStatusUpdateShipping(order_id);
			return "redirect:/admin/orders/view?order_id="+order_id +"&user_id="+user_id;
		}
		@GetMapping("/deliveryOk")
		public String adminOrderDeliveryGet() {			
			return "redirect:/admin/orders";
		}
		// 배송완료 처리
		@PostMapping("/deliveryOk")
		public String adminOrderDeliveryPost(@ModelAttribute OrdersVO ordersVO,Integer order_id, Integer user_id) {
			orderService.orderStatusUpdateDelivery(order_id);
			return "redirect:/admin/orders/view?order_id="+order_id +"&user_id="+user_id;
		}
		@GetMapping("/invoiceOk")
		public String adminOrderInvoiceGet() {			
			return "redirect:/admin/orders";
		}
		// 송장번호 저장
		@PostMapping("/invoiceOk")
		public String adminOrderInvoicePost(@ModelAttribute OrdersVO ordersVO,Integer order_id, Integer user_id) {
			orderService.updateInvoice(ordersVO);
			return "redirect:/admin/orders/view?order_id="+order_id +"&user_id="+user_id;
		}
		// 토스트 에디터 이미지 처리
		@ResponseBody
		@RequestMapping(value = "/image_upload.do", method = RequestMethod.POST)
		public String imageUpload(@RequestParam("image")MultipartFile multipartFile,
								  HttpServletRequest request) {
			String projectDir = System.getProperty("user.dir");
			String filePath = projectDir +  "/src/main/resources/static/images/products/toast";
			File file = new File(filePath);
			if(!file.exists()) file.mkdirs();
			String fileName = multipartFile.getOriginalFilename();
			int lastIndex = fileName.lastIndexOf(".");
			String ext = fileName.substring(lastIndex, fileName.length());
			String newFileName = LocalDate.now() + "_" + System.currentTimeMillis() + ext;

			try {
				File image = new File(filePath, newFileName);
				multipartFile.transferTo(image);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			return "/images/products/toast/" + newFileName;
		}
}
