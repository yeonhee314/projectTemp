package com.choongang.shoppingmall.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
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

import com.choongang.shoppingmall.service.ProductService;
import com.choongang.shoppingmall.service.UserService;
import com.choongang.shoppingmall.service.WishService; // WishService 추가
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ProductVO;
import com.choongang.shoppingmall.vo.UserVO;
import com.choongang.shoppingmall.vo.WishVO; // WishVO 추가

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WishService wishService; // WishService 주입

    // 로그인 여부 확인
    public boolean isUserLoggedin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

    // 회원 정보 가져오기
    public UserVO getUserInfo() {
        UserVO userVO = new UserVO();
        if (isUserLoggedin()) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            userVO = userService.selectByUsername(username);

            // wishList 초기화
            if (userVO.getWishList() == null) {
                userVO.setWishList(new ArrayList<>()); // 빈 리스트로 초기화
            }
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
    public String searchProducts(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "24") int size, // 페이지당 24개 상품
            @RequestParam(value = "keyword", defaultValue = "") String keyword, Model model) {
        try {
            // 상품 목록을 PagingVO로 가져옴
            PagingVO<ProductVO> pagingVO = productService.getProductList(page, size, 10, "product_name", keyword);

            // 상품 목록
            List<ProductVO> productList = pagingVO.getList();

            // 총 상품 개수
            int totalCount = pagingVO.getTotalCount();
            if(productList != null) {
                // 페이지네이션 처리 (페이지에 맞게 상품 목록을 자른다)
                List<ProductVO> paginatedProducts = productService.paginateProducts(productList, page, size, totalCount);
                model.addAttribute("products", paginatedProducts);
            }

            // 총 페이지 수 계산
            int totalPages = productService.calculateTotalPages(totalCount, size);

            // 현재 페이지 그룹의 시작과 끝을 계산 (예: 1~10, 11~20)
            int pageGroupSize = 10;
            int currentPageGroup = (page - 1) / pageGroupSize;
            int startPage = currentPageGroup * pageGroupSize + 1;
            int endPage = Math.min(startPage + pageGroupSize - 1, totalPages);

            // 이전, 다음 버튼의 활성화 여부 설정
            boolean showPrev = page > 1;
            boolean showNext = page < totalPages;
            boolean showFirst = page > 1;
            boolean showLast = page < totalPages;

            // 1~10 페이지 그룹에서 <<, < 비활성화 처리
            boolean disablePrevGroup = startPage <= 10; // 1~10 그룹일 때 <<, < 비활성화
            boolean disableFirstPage = startPage <= 10; // 1~10 그룹일 때 <<, < 비활성화

            // 회원 정보 가져오기
            UserVO userVO = getUserInfo();

            // 위시리스트 정보 가져오기 (로그인된 사용자일 경우)
            if (isUserLoggedin()) {
                List<WishVO> wishList = wishService.selectWishByUserId(userVO.getUser_id());
                userVO.setWishList(wishList); // 위시리스트 정보 추가
            }

            // 모델에 데이터 추가
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("keyword", keyword);
            model.addAttribute("uservo", userVO);
            model.addAttribute("showPrev", showPrev);
            model.addAttribute("showNext", showNext);
            model.addAttribute("showFirst", showFirst);
            model.addAttribute("showLast", showLast);
            model.addAttribute("disablePrevGroup", disablePrevGroup); // 비활성화 상태 추가
            model.addAttribute("disableFirstPage", disableFirstPage); // 비활성화 상태 추가

            log.info("검색어: {}, 페이지: {}, 총 상품 수: {}", keyword, page, totalCount);
        } catch (Exception e) {
            log.error("상품 목록 조회 실패", e);
            model.addAttribute("error", "상품 목록을 불러오는 데 실패했습니다.");
        }
        return "searchResult"; // 검색 결과 페이지로 이동
    }
}
