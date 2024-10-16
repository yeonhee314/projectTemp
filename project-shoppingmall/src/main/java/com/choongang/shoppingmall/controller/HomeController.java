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
import com.choongang.shoppingmall.service.ReviewService;
import com.choongang.shoppingmall.vo.CategoryVO;
import com.choongang.shoppingmall.vo.CommVO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ProductPagingVO;
import com.choongang.shoppingmall.vo.ProductVO;
import com.choongang.shoppingmall.vo.ReviewVO;
import com.choongang.shoppingmall.vo.UserVO;

@Controller
@Configuration
public class HomeController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/index.html")
	public String index(
						@ModelAttribute CommVO commVO, 
						@RequestParam(value = "category_id", defaultValue = "0") int category_id,
						Model model) {
		PagingVO<ProductVO> pv = null;
		List<CategoryVO> categorylist= categoryService.selectCategory();
		
		pv = category_id == 0 ? productService.getProductList(commVO.getCurrentPage(), commVO.getSizeOfPage(), commVO.getSizeOfBlock()) :
			 productService.getFilterProductList(category_id, commVO.getCurrentPage(), commVO.getSizeOfPage(), commVO.getSizeOfBlock());
		
		model.addAttribute("pv", pv);
		model.addAttribute("categorylist", categorylist);
		model.addAttribute("newLine", "\n" );
		model.addAttribute("br", "<br>" );
		
		return "index";
	}
	@GetMapping("/about.html")
	public String about() {
		return "about";
	}
	@GetMapping("/blog.html")
	public String blog() {
		return "blog";
	}
	@GetMapping("/blog-detail.html")
	public String blogDetail() {
		return "blog-detail";
	}
	@GetMapping("/contact.html")
	public String contact() {
		return "contact";
	}
	@GetMapping("/home-02.html")
	public String home02() {
		return "home-02";
	}
	@GetMapping("/home-03.html")
	public String home03() {
		return "home-03";
	}
	@GetMapping("/product.html")
	public String product() {
		return "product";
	}
	
	@GetMapping("/product-detail.html")
	public String productDetail(
			@RequestParam("product_id") int product_id,
			@RequestParam("category_id") int category_id,
			Model model) {
		ProductVO productVO = productService.selectByProductId(product_id);
		CategoryVO categoryVO = categoryService.selectCategoryId(category_id);
		int reviewCount = reviewService.selectReviewCount(product_id);
		double avgRating = reviewService.selectRating(product_id);
		model.addAttribute("productvo", productVO);
		model.addAttribute("categoryvo", categoryVO);
		model.addAttribute("reviewcount", reviewCount);
		model.addAttribute("avgrating", avgRating);
		
		return "product-detail";
	}
	
	
	@GetMapping("/product-review.html")
	public String productReview(
			@ModelAttribute CommVO commVO, 
			@RequestParam("product_id") int product_id,
			@RequestParam("category_id") int category_id, 
			Model model
			) {
		PagingVO<ReviewVO> pv = reviewService.getReviewList(product_id, commVO.getCurrentPage(), commVO.getSizeOfPage(), commVO.getSizeOfBlock());
		ProductVO productVO = productService.selectByProductId(product_id);
		CategoryVO categoryVO = categoryService.selectCategoryId(category_id);
		
		model.addAttribute("pv", pv);
		model.addAttribute("productvo", productVO);
		model.addAttribute("categoryvo", categoryVO);
		
		return "product-review";
	}
	
	@GetMapping("/shoping-cart.html")
	public String shopingCart() {
		return "shoping-cart";
	}
	@GetMapping("/orders.html")
	public String orders() {
		return "orders";
	}
	@GetMapping("/orderComplete.html")
	public String orderComplete() {
		return "orderComplete";
	}
}
