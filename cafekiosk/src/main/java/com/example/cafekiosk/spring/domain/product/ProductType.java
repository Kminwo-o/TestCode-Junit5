package com.example.cafekiosk.spring.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ProductType {
    HAND_MADE("제조 음료"),
    BOTTEL("병 음료"),
    BAKERY("베이커리");

    private final String text;

    public static boolean containsStockTypeCheck(ProductType productType) {
        return List.of(BOTTEL, BAKERY).contains(productType);
    }
}
