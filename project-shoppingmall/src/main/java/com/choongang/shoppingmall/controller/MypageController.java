package com.choongang.shoppingmall.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choongang.shoppingmall.service.QuestionCommentService;
import com.choongang.shoppingmall.service.AddressService;
import com.choongang.shoppingmall.service.OrderService;
import com.choongang.shoppingmall.service.QuestionService;
import com.choongang.shoppingmall.service.UserService;
import com.choongang.shoppingmall.service.WishService;
import com.choongang.shoppingmall.vo.QuestionCommentVO;
import com.choongang.shoppingmall.vo.AddressVO;
import com.choongang.shoppingmall.vo.MyPageReviewInfo;
import com.choongang.shoppingmall.vo.Order_ItemVO;
import com.choongang.shoppingmall.vo.OrdersVO;
import com.choongang.shoppingmall.vo.QuestionVO;
import com.choongang.shoppingmall.vo.UserVO;
import com.choongang.shoppingmall.vo.WishVO;

import jakarta.servlet.http.HttpSession;

@Controller
@Validated
public class MypageController {

	@Autowired
	private UserService userService;
	@Autowired
	private WishService wishService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private AddressService addressService;
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

	// 마이페이지
	@GetMapping("/myPage.html")
	public String myPage(Model model) {
		 
		boolean isLogin = isUserLoggedin();
		UserVO userVO = getUserInfo();
		if (!isLogin)
			return "redirect:/login";

		// 사용자의 주문 건수와 총합을 가져오기
		int userId = userVO.getUser_id(); // 사용자 ID 가져오기
		Map<String, Object> orderStats = orderService.getOrderStatsByUserId(userId);
		
		//사용자 주문정보 가져오기 
		List<OrdersVO> orders = orderService.getOrdersByUserId(userId);
		model.addAttribute("orders", orders);
		
		if (orderStats == null) {
		    orderStats = new HashMap<>();
		    orderStats.put("ORDER_COUNT", 0);  // 기본값
		    orderStats.put("TOTAL_SUM", 0);  // 기본값
		}

		model.addAttribute("isLogin", isLogin);
		model.addAttribute("uservo", userVO);
		model.addAttribute("order_count", orderStats.get("ORDER_COUNT"));
		model.addAttribute("total_sum", orderStats.get("TOTAL_SUM"));
	    
	    


		return "myPage";
	}

	// 회원혜택 (쿠폰/적립금)
	@GetMapping("/my-mileage.html")
	public String mymileage(Model model) {

		boolean isLogin = isUserLoggedin();
		UserVO userVO = getUserInfo();
		
		// 사용자의 주문 건수와 총합을 가져오기
				int userId = userVO.getUser_id(); // 사용자 ID 가져오기
				Map<String, Object> orderStats = orderService.getOrderStatsByUserId(userId);
				
		if (!isLogin)
			return "redirect:/login";
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("uservo", userVO);
		model.addAttribute("order_count", orderStats.get("ORDER_COUNT"));
		model.addAttribute("total_sum", orderStats.get("TOTAL_SUM"));
		

		return "/my-mileage.html";
	}

	// 후기 관리
	@GetMapping("/my-reviewList.html")
	public String reviewList(Model model) {
		if (!isUserLoggedin())
			return "redirect:/login";
		UserVO userVO = getUserInfo();
		boolean isLogin = isUserLoggedin();

		List<MyPageReviewInfo> infoList = orderService.selectByMyReview(userVO.getUser_id());
		int ableReviewCount = orderService.selectByMyReviewCount(userVO.getUser_id());

		model.addAttribute("uservo", userVO);
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("ableReviewCount", ableReviewCount);
		model.addAttribute("infoList", infoList);

		return "/my-reviewList.html";
	}

	// 문의 내역
	@GetMapping("/my-question.html")
	public String questionList(Model model) {
		if (!isUserLoggedin())
			return "redirect:/login";

		UserVO userVO = getUserInfo();
		boolean isLogin = isUserLoggedin();
		List<QuestionVO> list = questionService.selectQuestionListByUserId(userVO.getUser_id());
		List<QuestionCommentVO> commList = questionService.getCommList(userVO.getUser_id());

		model.addAttribute("list", list);
		model.addAttribute("commList", commList);
		model.addAttribute("uservo", userVO);
		model.addAttribute("isLogin", isLogin);

		return "/my-question.html";
	}

	// 배송지 추가
	@GetMapping("/my-addrList.html")
	public String addressList(HttpSession session, Model model) throws SQLException {

		int userId = (int) session.getAttribute("userId");

		List<AddressVO> addressList = addressService.getAddressList(userId);

		UserVO user = userService.getUserById(userId);

		if (!isUserLoggedin())
			return "redirect:/login";
		UserVO userVO = getUserInfo();
		boolean isLogin = isUserLoggedin();

		model.addAttribute("uservo", userVO);
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("user", user);
		model.addAttribute("addressVO", new AddressVO());
		model.addAttribute("addressList", addressList);

		return "/my-addrList.html";
	}

	// 배송지 수정목록 확인
	@GetMapping("/address/get/{addr_id}")
	@ResponseBody
	public ResponseEntity<AddressVO> getAddressById(@PathVariable("addr_id") int addr_id) {
		AddressVO address = addressService.getAddressByAddrId(addr_id);

		if (address != null) {
			return ResponseEntity.ok(address);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// 배송지 수정 처리
	@PostMapping("/address/update")
	public String updateAddress(AddressVO addressVO) {
		addressService.updateAddress(addressVO);
		return "redirect:/my-addrList.html"; // 배송지 목록으로 리다이렉트
	}

	// 배송지 삭제
	@GetMapping("/address/delete")
	public String deleteAddress(@RequestParam("addr_id") int addr_id) {
		addressService.deleteAddress(addr_id);
		return "redirect:/my-addrList.html"; // 배송지 목록으로 리다이렉트
	}

	// 배송지 선택 -> 유저 주소로 변경
	@PostMapping("/address/select")
	public String selectAddress(HttpSession session, Model model, @RequestParam("addr_id") int addr_id,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "postcode", required = false) String postcode,
			@RequestParam(name = "address", required = false) String address,
			@RequestParam(name = "address_detail", required = false) String address_detail) throws SQLException {

		int userId = (int) session.getAttribute("userId");

		List<AddressVO> addressList = addressService.getAddressList(userId);

		UserVO user = userService.getUserById(userId);

		if (!isUserLoggedin())
			return "redirect:/login";
		UserVO userVO = getUserInfo();
		boolean isLogin = isUserLoggedin();

		model.addAttribute("uservo", userVO);
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("user", user);
		model.addAttribute("addressVO", new AddressVO());
		model.addAttribute("addressList", addressList);

		addressService.updateUserAddress(userId, addr_id, name, postcode, address, address_detail);
		return "redirect:/my-addrList.html"; // 배송지 목록으로 리다이렉트
	}

	// 문의 내역 삭제
	@PostMapping("/deleteQuestion")
	public String deleteQuestion(@RequestParam("question_id") int question_id) {
		QuestionVO questionVO = questionService.selectById(question_id);
		questionService.deleteToQuestion(questionVO);
		return "redirect:/my-question.html";
	}

	// 회원 정보 확인
	@GetMapping("/my-modify.html")
	public String getProfile(HttpSession session, Model model) throws SQLException {
		if (!isUserLoggedin())
			return "redirect:/login";
		UserVO userVO = getUserInfo();
		boolean isLogin = isUserLoggedin();

		int userId = (int) session.getAttribute("userId");
		UserVO user = userService.getUserById(userId);

		// model.addAttribute("list", list);
		model.addAttribute("uservo", userVO);
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("user", user);

		return "/my-modify.html";
	}

	// 회원 정보 수정
	@PostMapping("/updateProfile")
	public String updateProfile(UserVO userVO, HttpSession session) throws SQLException {
		int userId = (int) session.getAttribute("userId");
		userVO.setUser_id(userId);
		userService.updateUser(userVO);

		return "redirect:/my-modify.html";

	}

}
