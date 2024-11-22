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
	OrdersVO selectOrderById(int order_id);
	List<Order_ItemVO> selectOrderItemByOrderId(int order_id);
	List<MyPageReviewInfo> selectByMyReview(int user_id);
	int selectByMyReviewCount(int user_id);
	void updateReviewStatus(int order_item_id);
	void addToOrder(OrdersVO vo);
	void addToOrderItems(Order_ItemVO vo);
	Integer selectMaxOrderId();
	Integer selectFirstOrdersId();
	Map<String, Object> getOrderStatsByUserId(int userId);
	
}
