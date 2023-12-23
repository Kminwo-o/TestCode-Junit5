package com.example.cafekiosk.unit;

import com.example.cafekiosk.unit.beverage.Americano;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CafeKioskTest {

    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 추가한 음료 수 : " + cafeKiosk.getBeverageList().size());
        for (int i = 0; i < cafeKiosk.getBeverageList().size(); i++) {
            System.out.println("담긴 음료 : " + cafeKiosk.getBeverageList().get(i).getName());
            System.out.println("음료 가격 : " + cafeKiosk.getBeverageList().get(i).getPrice());
        }
    }
}