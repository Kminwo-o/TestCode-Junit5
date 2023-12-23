package com.example.cafekiosk.unit.beverage;

public class Americano implements Beverage{
    @Override
    public int getPrice() {
        return 4500;
    }

    @Override
    public String getName() {
        return "Americano";
    }
}
