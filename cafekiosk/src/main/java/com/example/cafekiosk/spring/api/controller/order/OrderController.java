package com.example.cafekiosk.spring.api.controller.order;

import com.example.cafekiosk.spring.api.service.order.OrderService;
import com.example.cafekiosk.spring.api.service.order.request.OrderCreateRequest;
import com.example.cafekiosk.spring.api.service.order.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/new")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderCreateRequest request) {
        return ResponseEntity.ok()
                .body(orderService.createOrder(request, LocalDateTime.now()));
    }

    @GetMapping("/new")
    public void check(OrderCreateRequest request) {
        orderService.createOrder(request, LocalDateTime.now());
    }
}
