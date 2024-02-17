package com.example.cafekiosk.spring.domain.product;

import com.example.cafekiosk.spring.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    private ProductSellingType productSellingType;

    @Embedded
    private ProductInfo productInfo;

    @Builder
    public Product(ProductType productType, ProductSellingType productSellingType, ProductInfo productInfo) {
        this.productType = productType;
        this.productSellingType = productSellingType;
        this.productInfo = productInfo;
    }
}
