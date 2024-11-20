package com.choongang.shoppingmall.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.OrderDAO;
import com.choongang.shoppingmall.vo.AdminOrderPagingVO;
import com.choongang.shoppingmall.vo.MyPageReviewInfo;
import com.choongang.shoppingmall.vo.Order_ItemVO;
import com.choongang.shoppingmall.vo.OrdersVO;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderDAO orderDAO;
	
	@Override
	public void addToOrder(OrdersVO vo) {
		try {
			orderDAO.addToOrder(vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int selectOrderCount(HashMap<String, String> map) {
		int count = 0;
		try {
			count = orderDAO.selectOrderCount(map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	
	public void addToOrderItems(Order_ItemVO vo) {
		try {
			orderDAO.addToOrderItems(vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Integer selectMaxOrderId() {
		try {
			return orderDAO.selectMaxOrderId();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer selectFirstOrdersId() {
		try {
			return orderDAO.selectFirstOrdersId();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public AdminOrderPagingVO<OrdersVO> selectAdminOrderList(int currentPage, int sizeOfPage, int sizeOfBlock,
			String field, String search) {
		AdminOrderPagingVO<OrdersVO> ov = null;
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("field", field == null || field.trim().length()==0 ? null : field);
			map.put("search", search == null || search.trim().length()==0 ? null : search);
			int totalCount = orderDAO.selectOrderCount(map);
			ov = new AdminOrderPagingVO<>(totalCount, currentPage, sizeOfPage, sizeOfBlock);
			if(totalCount > 0) {
				map.put("startNo", ov.getStartNo()+"");
				map.put("endNo", ov.getEndNo()+"");
				List<OrdersVO> list = orderDAO.selectAdminOrderList(map);
				ov.setList(list);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return ov;
	}

	@Override
	public List<MyPageReviewInfo> selectByMyReview(int user_id) {
		List<MyPageReviewInfo> reviewInfo = null;
		try {
			reviewInfo = orderDAO.selectByMyReview(user_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviewInfo;
	}

	@Override
	public int selectByMyReviewCount(int user_id) {
		int count = 0;
		try {
			count = orderDAO.selectByMyReviewCount(user_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}
