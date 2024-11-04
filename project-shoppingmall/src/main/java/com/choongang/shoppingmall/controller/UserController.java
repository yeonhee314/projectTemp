package com.choongang.shoppingmall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choongang.shoppingmall.service.UserService;
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

    @PostMapping("/join")
    @ResponseBody // JSON 형식으로 응답
    public Map<String, String> joinUser(@ModelAttribute UserVO user) {
        Map<String, String> response = new HashMap<>();

        // 중복 확인
        int countUsername = userService.selectCountByUsername(user.getUsername());
        int countNickname = userService.selectCountByNickname(user.getNickname());
        int countPhone = userService.selectCountByPhone(user.getPhone());
        int countEmail = userService.selectCountByEmail(user.getEmail());

        if (countUsername > 0) {
            response.put("status", "fail");
            response.put("message", "이미 사용 중인 아이디입니다.");
            return response;
        }

        if (countNickname > 0) {
            response.put("status", "fail");
            response.put("message", "이미 사용 중인 닉네임입니다.");
            return response;
        }

        if (countPhone > 0) {
            response.put("status", "fail");
            response.put("message", "이미 사용 중인 전화번호입니다.");
            return response;
        }
        if (countEmail > 0) {
        	response.put("status", "fail");
        	response.put("message", "이미 사용 중인 이메일입니다.");
        	return response;
        }

        // 회원가입 성공
        userService.insert(user);
        response.put("status", "success");
        response.put("message", "회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.");
        return response;
    }
    
    //아이디 중복확인
    @GetMapping(value = "/test/usernameCheck", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String usernameCheck(@RequestParam(required = false,  defaultValue = "user") String username) {
        int count = userService.selectCountByUsername(username);
        log.info("아이디 중복 확인 요청: {}, 결과: {}", username, count);
        return String.valueOf(count);
    }
    //닉네임 중복확인
    @GetMapping(value = "/test/nicknameCheck", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String nicknameCheck(@RequestParam(required = false, defaultValue = "user") String nickname) {
    	int count = userService.selectCountByNickname(nickname);
    	log.info("닉네임 중복 확인 요청: {}, 결과: {}", nickname, count);
    	return String.valueOf(count);
    }
    //핸드폰 중복확인
    @GetMapping(value = "/test/phoneCheck", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String phoneCheck(@RequestParam(required = false, defaultValue = "user") String phone) {
    	int count = userService.selectCountByPhone(phone);
    	log.info("핸드폰 중복 확인 요청: {}, 결과: {}", phone, count);
    	return String.valueOf(count);
    }
    //이메일 중복확인
    @GetMapping(value = "/test/emailCheck", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String emailCheck(@RequestParam(required = false, defaultValue = "user") String email) {
    	int count = userService.selectCountByEmail(email);
    	log.info("이메일 중복 확인 요청: {}, 결과: {}", email, count);
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
    
    //아이디찾기페이지
    @GetMapping("/find-username")
    public String findUsernameForm() {
    	return "find-username";
    }
    
    //아이디찾기처리
    @PostMapping("/find-username")
    public String findUsername(@RequestParam("name") String name, @RequestParam("email") String email, Model model) {
    	log.info("아이디찾기 요청: 이름 = {}, 이메일 = {}", name, email);
    	String username = userService.selectByForgetUsername(name, email);
    	
    	if(username != null) {
    		log.info("아이디찾기 성공: {}", username);
    		model.addAttribute("username", username);
    		return "find-username-result";
    	} else {
    		log.warn("아이디 찾기 실패: 이름 = {}, 이메일 = {}", name, email);
    		model.addAttribute("error", "입력하신 정보와 일치하는 아이디를 찾을 수 없습니다.");
    		return "find-username";
    	}
    }
    
    //비밀번호찾기페이지
    @GetMapping("/find-password")
    public String findPasswordForm() {
    	return "find-password";
    }
    
    //비밀번호찾기(사용자조회)
    @PostMapping("/find-password")
    public String findPassword(@RequestParam("username") String username,
    							@RequestParam("phone") String phone,
    							@RequestParam("email") String email,
    							Model model) {
    	UserVO user = userService.selectByUserPW(username, phone, email);
    	
    	if (user != null) {
    		model.addAttribute("username", username);
    		return "reset-password";
    	} else {
    		model.addAttribute("error", "입력한 정보와 일치하는 계정이 없습니다");
    		return "find-password";
    	}
    }
    
    //비밀번호찾기(새비밀번호변경)
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("username") String username,
    							@RequestParam("password") String password,
    							Model model) {
    	userService.updatePassword(username, password);
    	model.addAttribute("message", "비밀번호가 변경되었습니다.");
    	return "login";
    }
}
