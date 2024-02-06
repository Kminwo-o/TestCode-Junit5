package com.example.cafekiosk.spring.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class ProductInfo {
    @Column
    private String name;

    @Column
    private int price;

    @Column
    private String productNumber;

    @Builder
    public ProductInfo(String name, int price, String productNumber) {
        this.name = name;
        this.price = price;
        this.productNumber = productNumber;
    }
}
