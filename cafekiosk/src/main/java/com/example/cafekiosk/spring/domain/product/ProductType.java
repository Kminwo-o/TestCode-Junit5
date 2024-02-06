package com.example.cafekiosk.spring.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {
    HAND_MADE("제조 음료"),
    BOTTEL("병 음료"),
    BAKERY("베이커리");

    private final String text;
}
