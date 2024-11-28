package com.choongang.shoppingmall.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choongang.shoppingmall.service.CategoryService;
import com.choongang.shoppingmall.service.OrderService;
import com.choongang.shoppingmall.service.ProductService;
import com.choongang.shoppingmall.service.ReviewService;
import com.choongang.shoppingmall.service.UserService;
import com.choongang.shoppingmall.service.WishService;
import com.choongang.shoppingmall.vo.CategoryVO;
import com.choongang.shoppingmall.vo.CommVO;
import com.choongang.shoppingmall.vo.Order_ItemVO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ProductVO;
import com.choongang.shoppingmall.vo.ReviewVO;
import com.choongang.shoppingmall.vo.UserVO;
import com.choongang.shoppingmall.vo.WishVO;

@Controller
public class ReviewController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private WishService wishService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	// 로그인 여부 확인
	public boolean isUserLoggedin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && authentication.isAuthenticated()
				&& !(authentication instanceof AnonymousAuthenticationToken);
	}

		// 회원 정보 가져오기
	public UserVO getUserInfo() {
		UserVO userVO = new UserVO();
		List<WishVO> wishList = null;
		if (isUserLoggedin()) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			userVO = userService.selectByUsername(username);
			wishList = wishService.selectWishByUserId(userVO.getUser_id());
			userVO.setWishList(wishList);

			}
			return userVO;
		}
	
	// 상품 상세 - 리뷰
	@GetMapping("/product-review.html")
	public String productReview(@ModelAttribute CommVO commVO, @RequestParam("product_id") int product_id,
			@RequestParam("category_id") int category_id, Model model) {
		PagingVO<ReviewVO> pv = reviewService.getReviewList(product_id, commVO.getCurrentPage(), commVO.getSizeOfPage(),
				commVO.getSizeOfBlock());
		ProductVO productVO = productService.selectByProductId(product_id);
		CategoryVO categoryVO = categoryService.selectCategoryId(category_id);
		UserVO userVO = getUserInfo();
		
		model.addAttribute("pv", pv);
		model.addAttribute("productvo", productVO);
		model.addAttribute("categoryvo", categoryVO);
		model.addAttribute("uservo", userVO);
		model.addAttribute("userService", userService);
		model.addAttribute("orderService", orderService);
		
		return "product-review";
	}
	
	// 후기 작성
	@GetMapping("/write-reviewPage.html")
	public String writeReview(Model model, 
							  @RequestParam("order_item_id") int order_item_id) {
		if (!isUserLoggedin())
			return "redirect:/login";
		
		Order_ItemVO orderItemVO = orderService.selectOrderItemByOrderItemId(order_item_id);
		ProductVO productVO = productService.selectByProductId(orderItemVO.getProduct_id());
				
		UserVO userVO = getUserInfo();
		model.addAttribute("uservo", userVO);
		model.addAttribute("orderItemVO", orderItemVO);
		model.addAttribute("productvo", productVO);
		
		return "write-reviewPage.html";
	}
	
	// 후기 수정
	@GetMapping("/modify-reviewPage.html")
	public String modifyReview(Model model, 
							  @RequestParam("review_id") int review_id,
							  @RequestParam("order_item_id") int order_item_id) {
		if (!isUserLoggedin())
			return "redirect:/login";
		
		ReviewVO reviewVO = reviewService.selectReviewByReviewId(review_id);
		Order_ItemVO orderItemVO = orderService.selectOrderItemByOrderItemId(order_item_id);
		ProductVO productVO = productService.selectByProductId(orderItemVO.getProduct_id());
				
		UserVO userVO = getUserInfo();
		model.addAttribute("uservo", userVO);
		model.addAttribute("orderItemVO", orderItemVO);
		model.addAttribute("productvo", productVO);
		model.addAttribute("reviewvo", reviewVO);
		
		return "write-reviewPage.html";
	}
	
	
	// 리뷰 등록
	@PostMapping("/submitWriteReview")
	public ResponseEntity<Map<String, String>> submitWriteReview(@ModelAttribute ReviewVO reviewVO){
		reviewService.addToReview(reviewVO);
		orderService.updateReviewStatus(reviewVO.getOrder_item_id());
		Map<String, String> response = new HashMap<>();
		response.put("message", "리뷰 등록 완료");
		return ResponseEntity.ok(response);
	}
	
	// 리뷰 수정
	@PostMapping("/updateToReview")
	public ResponseEntity<Map<String, String>> submitModifyReview(@ModelAttribute ReviewVO reviewVO){
		reviewService.updateToReview(reviewVO);
		Map<String, String> response = new HashMap<>();
		response.put("message", "리뷰 수정 완료");
		return ResponseEntity.ok(response);
	}
	
	// 리뷰 삭제
	@PostMapping("/deleteToReview")
	public ResponseEntity<Map<String, String>> deleteToReview(@RequestParam("review_id") int review_id) {
		reviewService.deleteToReview(review_id);
		Map<String, String> response = new HashMap<>();
		response.put("message", "리뷰 삭제 완료");
		return ResponseEntity.ok(response);
	}
	
	// 리뷰 상태 업데이트
	@PostMapping("/updateReviewStatus")
	@ResponseBody
	public void updateReviewStatus(@RequestParam("order_item_id") int order_item_id) {
		orderService.updateReviewStatus(order_item_id);
	}
}

