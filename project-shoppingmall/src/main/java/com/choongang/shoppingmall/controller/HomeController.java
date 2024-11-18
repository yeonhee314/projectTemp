package com.choongang.shoppingmall.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choongang.shoppingmall.service.CartService;
import com.choongang.shoppingmall.service.CategoryService;
import com.choongang.shoppingmall.service.ProductService;
import com.choongang.shoppingmall.service.ReviewService;
import com.choongang.shoppingmall.service.UserService;
import com.choongang.shoppingmall.service.WishService;
import com.choongang.shoppingmall.vo.CartVO;
import com.choongang.shoppingmall.vo.CategoryVO;
import com.choongang.shoppingmall.vo.CommVO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ProductVO;
import com.choongang.shoppingmall.vo.ReviewVO;
import com.choongang.shoppingmall.vo.UserVO;
import com.choongang.shoppingmall.vo.WishVO;

import jakarta.servlet.http.HttpSession;

@Controller
@Configuration
public class HomeController {

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
	private CartService cartService;

	private boolean isWish = false;

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

	@GetMapping("/index.html")
	public String index(@RequestParam(required = false, name = "field") String field,
			@RequestParam(required = false, name = "search") String search, @ModelAttribute CommVO commVO,
			Model model) {
		PagingVO<ProductVO> pv = productService.getProductList(commVO.getCurrentPage(), commVO.getSizeOfPage(),
				commVO.getSizeOfBlock(), field, search);
		List<CategoryVO> categorylist = categoryService.selectCategory();
		boolean isLogin = isUserLoggedin();
		UserVO userVO = getUserInfo();

		model.addAttribute("isLogin", isLogin);
		model.addAttribute("uservo", userVO);
		model.addAttribute("pv", pv);
		model.addAttribute("categorylist", categorylist);
		model.addAttribute("field", field);
		model.addAttribute("search", search);
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "<br>");

		return "index";
	}

	@PostMapping("/setWish")
	@ResponseBody
	public Boolean setWishList(@RequestBody Map<String, Integer> request) {
		WishVO vo = new WishVO();
		int user_id = request.get("user_id");
		int product_id = request.get("product_id");
		vo.setUser_id(user_id);
		vo.setProduct_id(product_id);

		isWish = wishService.isWishCount(user_id, product_id) == 1 ? true : false;
		if (isWish) {
			wishService.deleteToWishList(vo);
		} else {
			wishService.addToWishList(vo);
		}

		return !isWish;
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

	@GetMapping("/product.html")
	public String product(@ModelAttribute CommVO commVO, 
						  @RequestParam(required = false, name = "field") String field,
						  @RequestParam(required = false, name = "search") String search,
							Model model) {
		PagingVO<ProductVO> pv = productService.getProductList(commVO.getCurrentPage(), commVO.getSizeOfPage(),
				commVO.getSizeOfBlock(), field, search);
		List<CategoryVO> categorylist = categoryService.selectCategory();
		boolean isLogin = isUserLoggedin();
		UserVO userVO = getUserInfo();

		model.addAttribute("isLogin", isLogin);
		model.addAttribute("uservo", userVO);
		model.addAttribute("pv", pv);
		model.addAttribute("categorylist", categorylist);
		model.addAttribute("field", field);
		model.addAttribute("search", search);
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "<br>");
		
		return "product";
	}

	@GetMapping("/product-detail.html")
	public String productDetail(@RequestParam("product_id") int product_id,
			@RequestParam("category_id") int category_id, Model model) {
		ProductVO productVO = productService.selectByProductId(product_id);
		CategoryVO categoryVO = categoryService.selectCategoryId(category_id);
		UserVO userVO = getUserInfo();
		boolean isLogin = isUserLoggedin();
		int reviewCount = reviewService.selectReviewCount(product_id);
		double avgRating = reviewService.selectRating(product_id);
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("productvo", productVO);
		model.addAttribute("categoryvo", categoryVO);
		model.addAttribute("reviewcount", reviewCount);
		model.addAttribute("avgrating", avgRating);
		model.addAttribute("uservo", userVO);

		return "product-detail";
	}

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

		return "product-review";
	}

	// 장바구니 목록 확인
	@GetMapping("/shoping-cart.html")
	public String shopingCart(HttpSession session, Model model) throws SQLException {

		Integer userId = (Integer) session.getAttribute("userId");

		List<CartVO> cartItems = cartService.getCartItems(userId);
		int itemCount = cartService.getCartItemCount(userId);// 장바구니 개수 출력

		UserVO userVO = getUserInfo();

		if (userId == null) {
			return "redirect:/login"; // 로그인 안된 경우 로그인 페이지로 리다이렉트
		}

		int totalPrice = cartItems.stream()
				.mapToInt(
						item -> item.getDiscountPrice(item.getProductPrice(), item.getDiscount()) * item.getCartCount())
				.sum();

		// 모델에 장바구니와 총합 추가
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("uservo", userVO);
		model.addAttribute("itemCount", itemCount);

		// log.info("로그"+cartItems);

		return "shoping-cart";
	}

	@GetMapping("/wishlist.html")
	public String wishList(Model model) {
		if (!isUserLoggedin())
			return "redirect:/login";
		UserVO userVO = getUserInfo();
		List<ProductVO> productList = wishService.selectProductByUserId(userVO.getUser_id());
		model.addAttribute("productList", productList);
		model.addAttribute("uservo", userVO);

		return "wishlist";
	}

	@GetMapping("/question.html")
	public String question(Model model) {
		if (!isUserLoggedin())
			return "redirect:/login";
		UserVO userVO = getUserInfo();
		model.addAttribute("uservo", userVO);

		return "question";
	}

	// 상세 페이지에서 바로 구매
	@GetMapping("/direct-orders.html") 
	public String directOrders(@RequestParam("product_id") Integer product_id,
							   @RequestParam("productOption") String productOption,
							   @RequestParam("count") int count,
							   Model model){ 
		if (!isUserLoggedin())
			return "redirect:/login";
		UserVO userVO = getUserInfo();
		ProductVO productVO = productService.selectByProductId(product_id);
		
		model.addAttribute("count", count);
		model.addAttribute("productoption", productOption);
		model.addAttribute("productvo", productVO);
		model.addAttribute("uservo", userVO);
		
		return "orders";
	}
	
	// 장바구니에서 구매
	@GetMapping("/cart-orders.html") 
	public String cartOrders(Model model) throws SQLException{ 
		if (!isUserLoggedin())
			return "redirect:/login";
		UserVO userVO = getUserInfo();
		int userId = userVO.getUser_id();
		List<CartVO> cartItems = cartService.getCartItems(userId);
		int totalPrice = cartItems.stream().mapToInt(item -> item.getDiscountPrice(item.getProductPrice(), item.getDiscount()) * item.getCartCount()).sum();
		
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("uservo", userVO);
		model.addAttribute("cartItems", cartItems);
		
		return "orders";
	}
	
	@GetMapping("/orderComplete.html")
	public String orderComplete(Model model) {
		UserVO userVO = getUserInfo();	// 유저 정보 가져오기
		
		model.addAttribute("uservo", userVO);
		return "orderComplete"; 
	}
}
