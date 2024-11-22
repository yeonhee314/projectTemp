package com.choongang.shoppingmall.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.choongang.shoppingmall.service.OrderService;
import com.choongang.shoppingmall.service.ProductService;
import com.choongang.shoppingmall.service.UserService;
import com.choongang.shoppingmall.vo.MyPageReviewInfo;
import com.choongang.shoppingmall.vo.ProductVO;
import com.choongang.shoppingmall.vo.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api")
public class UserContentController {
    @Autowired
    private UserService userService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	
    @GetMapping("/membership")
    public ModelAndView getMembershipContent(HttpSession session, Model model) throws SQLException {
    	 Integer userId = (Integer) session.getAttribute("userId");
    	    if (userId == null) {
    	        throw new IllegalStateException("User not logged in");
    	    }

    	    UserVO uservo = userService.getUserById(userId);
    	    model.addAttribute("uservo", uservo);
    	    return new ModelAndView("membership");
    }

    @GetMapping("/points")
    public ModelAndView getPointsContent() {
        return new ModelAndView("points");
    }
    
    @GetMapping("/write-review")
    public ModelAndView getWriteReview(HttpSession session, Model model) {
    	 Integer userId = (Integer) session.getAttribute("userId");
 	    if (userId == null) {
 	        throw new IllegalStateException("User not logged in");
 	    }

 	    UserVO uservo = null;
		try {
			uservo = userService.getUserById(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<MyPageReviewInfo> infoList = orderService.selectByMyReview(uservo.getUser_id());
		int reviewCount = orderService.selectByMyReviewCount(uservo.getUser_id());
 	    model.addAttribute("uservo", uservo);
		model.addAttribute("reviewCount", reviewCount);
		model.addAttribute("infoList", infoList);
		model.addAttribute("productService", productService);
    	
        return new ModelAndView("write-review");
    }
    
    @GetMapping("/wrote-review")
    public ModelAndView getWroteReview(HttpSession session) {
        return new ModelAndView("wrote-review");
    }
}
