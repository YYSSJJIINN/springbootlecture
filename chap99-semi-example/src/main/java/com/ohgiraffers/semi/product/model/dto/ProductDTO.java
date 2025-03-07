package com.ohgiraffers.semi.product.model.dto;

public class ProductDTO implements java.io.Serializable {
    private int productCode;            // 상품코드
    private String productName;         // 상품명
    private int productPrice;           // 상품가격
    private String productDescription;  // 상품설명
    private String productOrderable;    // 구매가능여부
    private int categoryCode;           // 카테고리식별코드
    private String productImageUrl;     // 상품이미지경로
    private int productStock;           // 상품재고

    public ProductDTO() {}

    public ProductDTO(int productCode, String productName, int productPrice, String productDescription,
                      String productOrderable, int categoryCode, String productImageUrl, int productStock) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productOrderable = productOrderable;
        this.categoryCode = categoryCode;
        this.productImageUrl = productImageUrl;
        this.productStock = productStock;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductOrderable() {
        return productOrderable;
    }

    public void setProductOrderable(String productOrderable) {
        this.productOrderable = productOrderable;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productCode=" + productCode +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDescription='" + productDescription + '\'' +
                ", productOrderable='" + productOrderable + '\'' +
                ", categoryCode=" + categoryCode +
                ", productImageUrl='" + productImageUrl + '\'' +
                ", productStock=" + productStock +
                '}';
    }
} 