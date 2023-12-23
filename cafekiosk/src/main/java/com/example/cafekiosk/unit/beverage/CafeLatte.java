package com.example.cafekiosk.unit.beverage;

public class CafeLatte implements Beverage{
    @Override
    public int getPrice() {
        return 5000;
    }

    @Override
    public String getName() {
        return "Cafe Latte";
    }
}
