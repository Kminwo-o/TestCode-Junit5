package com.example.cafekiosk.spring.api.service;

import com.example.cafekiosk.spring.api.service.response.ProductResponse;

import java.util.List;

public interface ProductService {
    public List<ProductResponse> getSellingProducts();
}
