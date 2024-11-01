package com.choongang.shoppingmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.choongang.shoppingmall.service.AddressService;
import com.choongang.shoppingmall.vo.AddressVO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/address")
public class AddressController {
	
	@Autowired 
	private AddressService addressService;
	
	// 새로운 배송지 추가
    @PostMapping("/add")
    public String addAddress(@ModelAttribute AddressVO addressVO,HttpSession session) {
    	  Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            addressService.addAddress(addressVO, userId);
        }
        return "redirect:/address/list";
    }
    
   
    /*
    @PostMapping("/edit")
    public String editAddress(@ModelAttribute AddressVO addressVO) {
        Integer userId = getCurrentUserId(); // 현재 로그인한 사용자 ID 가져오기
        if (userId != null) {
            addressService.modifyAddress(addressVO, userId);
        }
        return "redirect:/address/list";
    }
     */
    
 
		
}
