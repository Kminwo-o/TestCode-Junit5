package com.example.cafekiosk.spring.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class ProductInfo {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String productNumber;

    @Builder
    public ProductInfo(String name, int price, String productNumber) {
        this.name = name;
        this.price = price;
        this.productNumber = productNumber;
    }
}
