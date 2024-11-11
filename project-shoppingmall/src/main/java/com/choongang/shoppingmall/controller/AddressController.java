package com.choongang.shoppingmall.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choongang.shoppingmall.service.AddressService;
import com.choongang.shoppingmall.vo.AddressVO;
import com.choongang.shoppingmall.vo.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class AddressController {
	
	@Autowired 
	private AddressService addressService;
	
	// 배송지 추가
	@PostMapping("/address/add")
	public String addAddress(@RequestParam("addr_name")String addr_name,@RequestParam("addr") String addr, @RequestParam("addr_detail")
							String addr_detail,@RequestParam("addr_code")String addr_code,@RequestParam("addr_id") int addr_id,
			HttpSession session,RedirectAttributes redirectAttributes,Model model) throws SQLException {
		
        // 사용자 로그인 여부 확인
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println("세션 아이디:" +userId);
        
        if (userId == null) {
            redirectAttributes.addFlashAttribute("message", "로그인 후 장바구니에 상품을 추가할 수 있습니다.");
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        addressService.addAddress(userId, addr_id, addr_name, addr, addr_detail, addr_code);
        redirectAttributes.addFlashAttribute("message", "배송지 추가 완료");
       
		return "redirect:/my-addrList.html";
	}
	
}
