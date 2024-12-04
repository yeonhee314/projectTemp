package com.choongang.shoppingmall.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choongang.shoppingmall.dao.ProductDAO;
import com.choongang.shoppingmall.vo.AdminProductsPagingVO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ProductVO;

import lombok.extern.slf4j.Slf4j;

@Service("productService")
@Slf4j
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDAO productDAO;

	@Override
	public PagingVO<ProductVO> getProductList(int currentPage, int sizeOfPage, int sizeOfBlock, String field, String search) {
		PagingVO<ProductVO> pv = null;
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("field", field == null || field.trim().length()==0 ? null : field);
			map.put("search", search == null || search.trim().length()==0 ? null : search);
			int totalCount = productDAO.selectProductCount(map);
			pv = new PagingVO<>(totalCount, currentPage, sizeOfPage, sizeOfBlock);
			if(totalCount > 0) {
				map.put("startNo", pv.getStartNo()+"");
				map.put("endNo", pv.getEndNo()+"");
				List<ProductVO> list = productDAO.selectProductList(map);
				pv.setList(list);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		//log.info("pv 리턴 : {}",pv);
		return pv;
	}
	@Override
	public AdminProductsPagingVO<ProductVO> getAdminProductList(int currentPage, int sizeOfPage, int sizeOfBlock, String field, String search) {
		AdminProductsPagingVO<ProductVO> pv = null;
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("field", field == null || field.trim().length()==0 ? null : field);
			map.put("search", search == null || search.trim().length()==0 ? null : search);
			int totalCount = productDAO.selectProductCount(map);
			pv = new AdminProductsPagingVO<>(totalCount, currentPage, sizeOfPage, sizeOfBlock);
			if(totalCount > 0) {
				map.put("startNo", pv.getStartNo()+"");
				map.put("endNo", pv.getEndNo()+"");
				List<ProductVO> list = productDAO.selectProductList(map);
				pv.setList(list);
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

	@Override
	public int selectProductCount(HashMap<String, String> map) {
		int count = 0;
		try {
			count = productDAO.selectProductCount(map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public String selectProductNameById(int product_id) {
		String pdname = "";
		try {
			pdname = productDAO.selectProductNameById(product_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pdname;
	}
	@Override
	public int selectYCount() {
		int count = 0;
		try {
			count = productDAO.selectYCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int selectSoldOutCount() {
		int count = 0;
		try {
			count = productDAO.selectSoldOutCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public void updateStatus(ProductVO productVO) {
		try {
			productDAO.updateStatus(productVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	// 상품 재고 변경
	@Override
	public void updateStock(ProductVO productVO) {
		try {
			productDAO.updateStock(productVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public int selectNCount() {
		int count = 0;
		try {
			count = productDAO.selectNCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public PagingVO<ProductVO> searchProducts(int page, int size, String keyword) {
        // 상품 목록을 PagingVO로 가져오기
        return getProductList(page, size, 10, "product_name", keyword);
    }

    public List<ProductVO> paginateProducts(List<ProductVO> productList, int page, int size, int totalCount) {
        // 페이지네이션 처리 (페이지에 맞게 상품 목록을 자른다)
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, totalCount);
        return productList.subList(startIndex, endIndex);
    }

    public int calculateTotalPages(int totalCount, int size) {
        return (int) Math.ceil((double) totalCount / size);
    }
}
