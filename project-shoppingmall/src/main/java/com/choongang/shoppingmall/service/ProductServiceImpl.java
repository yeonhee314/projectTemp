package com.choongang.shoppingmall.service;

import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.ProductDAO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ProductVO;

import lombok.extern.slf4j.Slf4j;

@Service("productService")
@Slf4j
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDAO productDAO;

	@Override
	public PagingVO<ProductVO> getProductList(int currentPage, int sizeOfPage, int sizeOfBlock) {
		PagingVO<ProductVO> pv = null;
		
		try {
			int totalCount = productDAO.selectProductCount();
			pv = new PagingVO<>(totalCount, currentPage, sizeOfPage, sizeOfBlock);
			if(totalCount > 0) {
				HashMap<String, Integer> map = new HashMap<>();
				map.put("startNo", pv.getStartNo());
				map.put("endNo", pv.getEndNo());
				pv.setList(productDAO.selectProductList(map));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		//log.info("pv 리턴 : {}",pv);
		return pv;
	}
	
	@Override
	public ProductVO selectByProductId(int id) {
		ProductVO productVO = null;
		try {
			productVO = productDAO.selectByProductId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return productVO;
	}

	@Override
	public PagingVO<ProductVO> getFilterProductList(int categoryId, int currentPage, int sizeOfPage, int sizeOfBlock) {
		PagingVO<ProductVO> pv = null;
		
		try {
			int totalCount = productDAO.selectFilterProductCount(categoryId);
			pv = new PagingVO<>(categoryId, totalCount, currentPage, sizeOfPage, sizeOfBlock);
			if(totalCount > 0) {
				HashMap<String, Integer> map = new HashMap<>();
				map.put("startNo", pv.getStartNo());
				map.put("endNo", pv.getEndNo());
				map.put("category_id", categoryId);
				pv.setList(productDAO.selectFilterProductList(map));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pv;
	}

	@Override
	public void insert(ProductVO productVO) {
		try {
			productDAO.insert(productVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("상품등록 리턴 : {}", productVO);
	}

	@Override
	public void update(ProductVO productVO) {
		try {
			productDAO.update(productVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("상품수정 리턴 : {}", productVO);
	}

	@Override
	public void delete(int product_id) {
		try {
			productDAO.delete(product_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("상품삭제 리턴 : {}", product_id);
	}

	@Override
	public int selectCountByProductName(String product_name) {
		int count = 1;
		try {
			count = productDAO.selectCountByProductName(product_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
