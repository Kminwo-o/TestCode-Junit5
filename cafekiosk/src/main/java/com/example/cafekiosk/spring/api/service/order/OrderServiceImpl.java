package com.example.cafekiosk.spring.api.service.order;

import com.example.cafekiosk.spring.api.service.order.request.OrderCreateRequest;
import com.example.cafekiosk.spring.api.service.order.response.OrderResponse;
import com.example.cafekiosk.spring.domain.order.Order;
import com.example.cafekiosk.spring.domain.order.OrderRepository;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        // Product
        List<Product> products = productRepository.findAllByProductInfo_ProductNumberIn(request.getProductNumbers());

        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        // Order
        return OrderResponse.of(savedOrder);
    }
}
