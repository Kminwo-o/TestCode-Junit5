package com.example.cafekiosk.unit.beverage;

import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AmericanoTest {
    @Test
    void getPrice() {
        Americano americano = new Americano();

        assertThat(americano.getPrice() == 4500);
    }

    @Test
    void getName() {
        Americano americano = new Americano();
        assertThat(americano.getName().equals("Americano"));
    }
}