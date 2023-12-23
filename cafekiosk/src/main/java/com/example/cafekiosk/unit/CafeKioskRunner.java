package com.example.cafekiosk.unit;

import com.example.cafekiosk.unit.beverage.Americano;
import com.example.cafekiosk.unit.beverage.CafeLatte;

public class CafeKioskRunner {
    public static void main(String[] args) {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        System.out.println(">>> 아메리카노 추가");
        
        cafeKiosk.add(new CafeLatte());
        System.out.println((">>> 카페라떼 추가"));

        int totalPrice = cafeKiosk.calculateTotalPrice();
        System.out.println("총 주문가격 : " + totalPrice);
    }
}