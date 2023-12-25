package com.example.cafekiosk.unit;

import com.example.cafekiosk.unit.beverage.Americano;
import com.example.cafekiosk.unit.beverage.CafeLatte;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        CafeLatte cafeLatte = new CafeLatte();
        cafeKiosk.add(cafeLatte);

        cafeKiosk.remove(cafeLatte);
        assertThat(cafeKiosk.getBeverageList()).hasSize(0);
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
}