package com.choongang.shoppingmall.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choongang.shoppingmall.dao.OrderDAO;
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
}
