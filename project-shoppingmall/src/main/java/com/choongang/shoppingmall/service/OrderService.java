package com.choongang.shoppingmall.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.choongang.shoppingmall.vo.AdminOrderPagingVO;
import com.choongang.shoppingmall.vo.MyPageReviewInfo;
import com.choongang.shoppingmall.vo.Order_ItemVO;
import com.choongang.shoppingmall.vo.OrdersVO;

public interface OrderService {
	int selectOrderCount(HashMap<String, String> map);
	AdminOrderPagingVO<OrdersVO> selectAdminOrderList(int currentPage, int sizeOfPage, int sizeOfBlock, String field, String search);
	List<MyPageReviewInfo> selectByMyReview(int user_id);
	int selectByMyReviewCount(int user_id);
	void addToOrder(OrdersVO vo);
	void addToOrderItems(Order_ItemVO vo);
	Integer selectMaxOrderId();
	Integer selectFirstOrdersId();
	
	Map<String, Object> getOrderStatsByUserId(int userId);
	
	List<OrdersVO> getOrdersByUserId(int userId);

}
