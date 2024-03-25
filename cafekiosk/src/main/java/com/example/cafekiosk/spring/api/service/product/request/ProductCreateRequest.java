package com.example.cafekiosk.spring.api.service.product.request;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductInfo;
import com.example.cafekiosk.spring.domain.product.ProductSellingType;
import com.example.cafekiosk.spring.domain.product.ProductType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotNull
    private ProductType productType;

    @NotNull
    private ProductSellingType productSellingType;

    @NotBlank
    private ProductInfo productInfo;

    @Builder
    private ProductCreateRequest(ProductType productType, ProductSellingType productSellingType, ProductInfo productInfo) {
        this.productType = productType;
        this.productSellingType = productSellingType;
        this.productInfo = productInfo;
    }

    public Product toEntity(String productNumber) {
        return Product.builder()
                .productType(this.productType)
                .productSellingType(this.productSellingType)
                .productInfo(ProductInfo.builder()
                        .productNumber(productNumber)
                        .price(this.productInfo.getPrice())
                        .name(this.productInfo.getName())
                        .build())
                .build();
    }
}
