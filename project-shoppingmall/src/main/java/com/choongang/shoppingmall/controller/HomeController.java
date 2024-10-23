package com.choongang.shoppingmall.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
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

import com.choongang.shoppingmall.service.CartListService;
import com.choongang.shoppingmall.service.CategoryService;
import com.choongang.shoppingmall.service.ProductService;
import com.choongang.shoppingmall.service.ReviewService;
import com.choongang.shoppingmall.service.UserService;
import com.choongang.shoppingmall.service.WishService;
import com.choongang.shoppingmall.vo.CartItem;
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
	private CartListService cartListService;

	
	// 로그인 여부 확인
	public boolean isUserLoggedin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
	}
	
	// 사용자 정보 가져오기
	public UserVO getUserInfo() {
		UserVO vo = new UserVO();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isLogin = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
		if(isLogin) {
			String username = authentication.getName();
			vo = userService.selectByUsername(username);
		}
		return vo;
	}
    
    @GetMapping("/index.html")
	public String index(
						@ModelAttribute CommVO commVO, 
						Model model) {
		PagingVO<ProductVO> pv = productService.getProductList(commVO.getCurrentPage(), commVO.getSizeOfPage(), commVO.getSizeOfBlock());
		List<CategoryVO> categorylist= categoryService.selectCategory();
		UserVO userVO = getUserInfo();
		boolean isLogin = isUserLoggedin();
		
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("uservo", userVO);
		model.addAttribute("pv", pv);
		model.addAttribute("categorylist", categorylist);
		model.addAttribute("newLine", "\n" );
		model.addAttribute("br", "<br>" );
		
		return "index";
	}
    
    @PostMapping("/addWish")
    public ResponseEntity<String> addToWishList(@RequestBody Map<String, Integer> request){
    	WishVO vo = new WishVO();
    	int user_id = request.get("user_id");
    	int product_id = request.get("product_id");
    	vo.setUser_id(user_id);
    	vo.setProduct_id(product_id);
    	
    	wishService.addToWishList(vo);
    	return ResponseEntity.ok("상품을 찜 목록에 담았습니다!");
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
	
	@GetMapping("/wishlist.html")
	public String wishList() {
		
		return "wishlist";
	}

	
	@GetMapping("/shoping-cart.html")
    public String shopingCart(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        List<CartItem> cartItems = cartListService.getCartItems(userId);
        for (CartItem item : cartItems) {
            System.out.println("Product: " + item.getProductName() + ", Price: " + item.getCartPrice());
        }
        
        if (userId == null) {
            return "redirect:/login"; // 로그인 안 된 경우 로그인 페이지로 리다이렉트
        }

     
        model.addAttribute("cartItems", cartItems);

        return "shoping-cart"; // 장바구니 화면으로 이동
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
