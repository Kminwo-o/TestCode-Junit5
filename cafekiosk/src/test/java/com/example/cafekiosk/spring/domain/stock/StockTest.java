package com.example.cafekiosk.spring.domain.stock;

import com.example.cafekiosk.spring.exception.StockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@DataJpaTest
class StockTest {

    @DisplayName("재고의 수량이 요청된 수량보다 작은지 확인한다.")
    @Test
    void isQuantityLessThan() {
        // given
        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 2);
        int quantity = 2;

        //when
        boolean result1 = stock1.isQuantityLessThan(quantity);
        boolean result2 = stock2.isQuantityLessThan(quantity);

        //then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
    @Test
    void deductQuantity() {
        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 1;

        // when
        stock.deductQuantity(quantity);

        // then
        assertThat(stock.getQuantity()).isZero();
    }

    @DisplayName("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다.")
    @Test
    void deductQuantityException() {
        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        // when, then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(StockException.class)
                .hasMessage("재고가 부족합니다.");
    }
}