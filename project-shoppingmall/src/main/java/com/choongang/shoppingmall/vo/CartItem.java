package com.choongang.shoppingmall.vo;



public class CartItem {
	
	private int cartId;  // 장바구니 고유번호
	private int userId;	// 회원 고유번호
	private int productId;     // 상품 ID
	private int cartCount;     // 장바구니에 담긴 수량
	private int cartPrice; // 장바구니 총금액
	
    private String productName; // 상품 이름
    private String productOption; // 상품 옵션
    
    private int productPrice;  // 상품 가격
   

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCartCount() {
		return cartCount;
	}

	public void setCartCount(int cartCount) {
		this.cartCount = cartCount;
	}

	public int getCartPrice() {
		return cartPrice;
	}

	public void setCartPrice(int cartPrice) {
		this.cartPrice = cartPrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductOption() {
		return productOption;
	}

	public void setProductOption(String productOption) {
		this.productOption = productOption;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}


	@Override
	public String toString() {
		return "CartItem [cartId=" + cartId + ", userId=" + userId + ", productId=" + productId + ", cartCount="
				+ cartCount + ", cartPrice=" + cartPrice + ", productName=" + productName + ", productOption="
				+ productOption + ", productPrice=" + productPrice + ", productVO= ]";
	}
    

}