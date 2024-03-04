package com.example.cafekiosk.spring.domain.order;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductInfo;
import com.example.cafekiosk.spring.domain.product.ProductSellingType;
import com.example.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {
    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice() {
        // given
        List<Product> products = List.of(
                createProduct("001", 1000, "포테이토"),
                createProduct("002", 2000, "스위트 포테이토")
        );

        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        assertThat(order.getTotalPrice()).isEqualTo(3000);
    }

    @DisplayName("주문 생성 시, 주문 상태는 INIT이다.")
    @Test
    void init() {
        // given
        List<Product> products = List.of(
                createProduct("001", 1000, "포테이토"),
                createProduct("002", 2000, "스위트 포테이토")
        );

        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }

    @DisplayName("주문 생성 시, 등록 시간을 기록한다.")
    @Test
    void registeredDateTime() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();
        List<Product> products = List.of(
                createProduct("001", 1000, "포테이토"),
                createProduct("002", 2000, "스위트 포테이토")
        );

        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }

    private Product createProduct (String productNumber, int price, String name) {
        return Product.builder()
                .productType(ProductType.HAND_MADE)
                .productSellingType(ProductSellingType.SELLING)
                .productInfo(ProductInfo.builder()
                        .name(name)
                        .productNumber(productNumber)
                        .price(price)
                        .build())
                .build();
    }
}