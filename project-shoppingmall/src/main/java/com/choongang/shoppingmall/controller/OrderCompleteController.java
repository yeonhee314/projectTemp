package com.choongang.shoppingmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.choongang.shoppingmall.service.OrderService;
import com.choongang.shoppingmall.vo.Order_CompleteVO;
import com.choongang.shoppingmall.vo.OrdersVO;
import com.choongang.shoppingmall.vo.UserVO;

@RestController
@RequestMapping("templates/orderComplete") // 기본 URL
public class OrderCompleteController {

    @Autowired
    private OrderService orderService;

    // 주문 완료 페이지 표시
    @GetMapping("templates/orderComplete")
    public String showOrderCompletePage(@RequestParam int orderId, Model model) {
        try {
            List<Order_CompleteVO> items = orderService.getOrderCompleteByOrderId(orderId);
            OrdersVO order = orderService.getOrderByid(orderId);
            
            // 사용자 정보 임시 객체
            UserVO user = new UserVO();
            user.setName("홍길동");
            user.setPhone("010-1234-5678");
            
            model.addAttribute("order", order);
            model.addAttribute("user", user);
            model.addAttribute("items", items); // items를 모델에 추가
            
            return "orderComplete"; // orderComplete.html을 반환
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // 에러 페이지 반환
        }
    }
}
