package com.choongang.shoppingmall.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choongang.shoppingmall.service.CategoryService;
import com.choongang.shoppingmall.service.ProductService;
import com.choongang.shoppingmall.service.UserService;
import com.choongang.shoppingmall.vo.CategoryVO;
import com.choongang.shoppingmall.vo.CommVO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ProductVO;
import com.choongang.shoppingmall.vo.UserVO;

import lombok.extern.slf4j.Slf4j;


@Controller
@Configuration
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// 약관 동의 페이지
    @GetMapping("/terms")
    public String terms() {
        return "terms";
    }

	// 회원가입 페이지
    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("user", new UserVO()); // 빈 UserVO 객체를 모델에 추가
        return "join";
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String JoinUser(@ModelAttribute UserVO user) {
        // 중복 확인
        int count = userService.selectCountByUsername(user.getUsername());
        if (count > 0) {
            return "redirect:/join"; // 오류 발생 시 다시 회원가입 페이지로 리다이렉트
        }

        // 회원가입
        userService.insert(user);
        return "redirect:/login"; // 회원가입 후 login 페이지로 리다이렉트
    }
    
    //아이디 중복확인
    @GetMapping(value = "/test/userIdCheck", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String userIdCheck(@RequestParam(required = false, defaultValue = "user") String username) {
        int count = userService.selectCountByUsername(username);
        log.info("아이디 중복 확인 요청: {}, 결과: {}", username, count);
        return String.valueOf(count);
    }
   
    //로그인페이지
    @GetMapping("/login")
    public String login() {
        return "login"; 
    }
    
    //홈페이지
    @GetMapping("/home")
    public String home() {
    	return "home";
    }
    
    @Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
    
    @GetMapping(value = {"/","/index"})
	public String index(
						@ModelAttribute CommVO commVO, 
						Model model) {
		PagingVO<ProductVO> pv = productService.getProductList(commVO.getCurrentPage(), commVO.getSizeOfPage(), commVO.getSizeOfBlock());
		List<CategoryVO> categorylist= categoryService.selectCategory();
		model.addAttribute("pv", pv);
		model.addAttribute("categorylist", categorylist);
		model.addAttribute("newLine", "\n" );
		model.addAttribute("br", "<br>" );
		
		return "index";
	}
}
