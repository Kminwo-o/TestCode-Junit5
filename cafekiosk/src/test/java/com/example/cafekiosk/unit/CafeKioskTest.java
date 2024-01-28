package com.example.cafekiosk.unit;

import com.example.cafekiosk.unit.beverage.Americano;
import com.example.cafekiosk.unit.beverage.CafeLatte;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CafeKioskTest {
    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverageList()).hasSize(1);
        assertThat(cafeKiosk.getBeverageList().get(0).getPrice()).isEqualTo(4500);
        assertThat(cafeKiosk.getBeverageList().get(0).getName()).isEqualTo("Americano");
    }

    @Test
    void addSeveralBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 2);

        // 해피 케이스 테스트
        assertThat(cafeKiosk.getBeverageList().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverageList().get(1)).isEqualTo(americano);
    }

    @Test
    void addZeroBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        /* 예외 케이스 테스트
        * 예외발생하는 메서드를 람다식으로 입력
        * hasMessage를 사용해 에러 메세지 기입 가능
        * */
        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("음료를 1잔 이상 주문하셔야합니다.");
    }

    @Test
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        CafeLatte cafeLatte = new CafeLatte();
        cafeKiosk.add(cafeLatte);

        cafeKiosk.remove(cafeLatte);
        assertThat(cafeKiosk.getBeverageList()).isEmpty();
    }

    @Test
    void clear() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        cafeKiosk.add(new CafeLatte());

        assertThat(cafeKiosk.getBeverageList()).hasSize(2);
        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverageList().isEmpty());
    }

    @Test
    void OpenTimeCreateOrder() {
        LocalDateTime localDateTime = LocalDateTime.of(2023,1, 16, 10, 0);

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        cafeKiosk.createOrder(localDateTime);
        assertThat(cafeKiosk.getBeverageList().get(0).getName()).isEqualTo("Americano");
        assertThat(cafeKiosk.getBeverageList()).hasSize(1);
    }

    @Test
    void CloseTimeCreateOrder() {
        LocalDateTime localDateTime = LocalDateTime.of(2023,1, 16, 9, 59);

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        assertThatThrownBy(() -> cafeKiosk.createOrder(localDateTime))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
    }
}