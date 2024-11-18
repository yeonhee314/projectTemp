package com.choongang.shoppingmall.dao;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.shoppingmall.vo.OrdersVO;

@Mapper
public interface OrderDAO {
	void addToOrder(OrdersVO vo) throws SQLException;
}
