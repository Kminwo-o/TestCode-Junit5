package com.example.cafekiosk.spring.api.service.response;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductInfo;
import com.example.cafekiosk.spring.domain.product.ProductSellingType;
import com.example.cafekiosk.spring.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {
    private Long id;
    private ProductType productType;
    private ProductSellingType productSellingType;
    private ProductInfo productInfo;

    @Builder
    private ProductResponse(Long id, ProductType productType, ProductSellingType productSellingType, ProductInfo productInfo) {
        this.id = id;
        this.productType = productType;
        this.productSellingType = productSellingType;
        this.productInfo = productInfo;
    }

    public static ProductResponse of (Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productType(product.getProductType())
                .productSellingType(product.getProductSellingType())
                .productInfo(product.getProductInfo())
                .build();
    }
}
