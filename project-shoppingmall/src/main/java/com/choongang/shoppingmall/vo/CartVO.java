package com.choongang.shoppingmall.vo;

public class CartVO {
    private int cartId;      // 장바구니 항목 ID
    private int userId;          // 사용자 ID
    private int productId;       // 상품 ID
    private String productName;   // 상품명
    private int cartCount;        // 수량
    private int productPrice;     // 상품 가격

    // Getters and Setters
    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getCartCount() { return cartCount; }
    public void setCartCount(int cartCount) { this.cartCount = cartCount; }

    public int getProductPrice() { return productPrice; }
    public void setProductPrice(int productPrice) { this.productPrice = productPrice; }
}
