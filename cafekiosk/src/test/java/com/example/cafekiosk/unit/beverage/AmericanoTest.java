package com.example.cafekiosk.unit.beverage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AmericanoTest {

    @Test
    void getPrice() {
        Americano americano = new Americano();

        assertEquals(americano.getPrice(), 4500);
    }

    @Test
    void getName() {
        Americano americano = new Americano();

        assertEquals(americano.getName(), "Americano");
    }
}