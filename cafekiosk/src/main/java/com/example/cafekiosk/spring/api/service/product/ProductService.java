package com.example.cafekiosk.spring.api.service.product;

import com.example.cafekiosk.spring.api.service.product.response.ProductResponse;

import java.util.List;

public interface ProductService {
    public List<ProductResponse> getSellingProducts();
}
