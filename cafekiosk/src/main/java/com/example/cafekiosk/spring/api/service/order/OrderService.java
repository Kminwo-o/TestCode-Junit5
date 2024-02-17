package com.example.cafekiosk.spring.api.service.order;

import com.example.cafekiosk.spring.api.service.order.request.OrderCreateRequest;
import com.example.cafekiosk.spring.api.service.order.response.OrderResponse;

import java.time.LocalDateTime;

public interface OrderService {
    OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime);
}
