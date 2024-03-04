package com.example.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType() {
        // given
        ProductType givenType1 = ProductType.HAND_MADE;
        ProductType givenType2 = ProductType.BAKERY;
        ProductType givenType3 = ProductType.BOTTEL;

        // when
        boolean result1 = ProductType.containsStockTypeCheck(givenType1);
        boolean result2 = ProductType.containsStockTypeCheck(givenType2);
        boolean result3 = ProductType.containsStockTypeCheck(givenType3);

        // then
        assertThat(result1).isFalse();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
    }

}