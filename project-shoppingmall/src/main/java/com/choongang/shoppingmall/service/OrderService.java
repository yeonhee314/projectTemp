package com.choongang.shoppingmall.service;

import com.choongang.shoppingmall.vo.Order_ItemVO;
import com.choongang.shoppingmall.vo.OrdersVO;

public interface OrderService {
	void addToOrder(OrdersVO vo);
	void addToOrderItems(Order_ItemVO vo);
	Integer selectMaxOrderId();
	Integer selectFirstOrdersId();
}
