package com.choongang.shoppingmall.controller;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.choongang.shoppingmall.dao.ProductDAO;
import com.choongang.shoppingmall.service.UserService;
import com.choongang.shoppingmall.service.WishService;
import com.choongang.shoppingmall.vo.ProductVO;
import com.choongang.shoppingmall.vo.UserVO;
import com.choongang.shoppingmall.vo.WishVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class ProductController {

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private WishService wishService;
	@Autowired
	private UserService userService;

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
	
	// 홈 페이지
	@GetMapping("/")
	public String home() {
		return "home"; // 홈 화면
	}

	// 검색 기능
	@GetMapping("/search")
	public String search(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "keyword", required = false) String keyword, // 검색어
			Model model) {
		
		try {
			// 페이징 계산
			int startNo = (page - 1) * size + 1; // 시작 번호 (1부터 시작)
			int endNo = page * size; // 끝 번호

			// 검색어와 페이징을 HashMap으로 묶어서 전달
			HashMap<String, String> params = new HashMap<>();
			params.put("field", "product_name"); // 고정된 필드 사용
			if (keyword != null && !keyword.trim().isEmpty()) {
				params.put("search", keyword.trim()); // 검색어 추가
			}
			params.put("startNo", String.valueOf(startNo)); // 숫자를 문자열로 변환
			params.put("endNo", String.valueOf(endNo)); // 숫자를 문자열로 변환

			// 상품 목록 조회
			List<ProductVO> productList = productDAO.selectProductList(params);
			int totalCount = productDAO.selectProductCount(params); // 총 상품 개수 조회

			// 가격 포맷팅 처리 (Map 형태로 전달)
			HashMap<Integer, String> formattedPrices = new HashMap<>();
			for (ProductVO product : productList) {
			    formattedPrices.put(product.getProduct_id(), formatPrice(product.getProduct_price()));
			}
			// 페이징 계산
			int totalPages = (int) Math.ceil((double) totalCount / size);
			

	        // 회원 정보 가져오기
	        UserVO userVO = getUserInfo();

			// 모델에 데이터 추가
			model.addAttribute("products", productList);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("keyword", keyword);
			model.addAttribute("formattedPrices", formattedPrices);
			model.addAttribute("uservo", userVO);

			log.info("검색어: {}, 페이지: {}, 총 상품 수: {}", keyword, page, totalCount);
		} catch (Exception e) {
			log.error("상품 목록 조회 실패", e);
			model.addAttribute("error", "상품 목록을 불러오는 데 실패했습니다.");
		}
		return "searchResult"; // 검색 결과 페이지로 이동
	}
	
	// formatPrice 메서드 제거
	private String formatPrice(int price) {
	    NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA); // 한국식 숫자 포맷
	    return numberFormat.format(price) + " \\";
	}

}
