package com.choongang.shoppingmall.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.choongang.shoppingmall.service.UserService;
import com.choongang.shoppingmall.vo.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api")
public class UserContentController {
    @Autowired
    private UserService userService;
	
    @GetMapping("/membership")
    public ModelAndView getMembershipContent(HttpSession session,Model model) throws SQLException {
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
}
