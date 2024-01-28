package com.example.cafekiosk.unit;

import com.example.cafekiosk.unit.beverage.Americano;
import com.example.cafekiosk.unit.beverage.CafeLatte;
import com.example.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

public class CafeKioskRunner {
    public static void main(String[] args) {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        cafeKiosk.add(new CafeLatte());

        Order order = cafeKiosk.createOrder(LocalDateTime.now());
        System.out.println(order.getBeverages().get(0).getName());
    }
}
