package com.example.cafekiosk.spring.api.service.order;

import com.example.cafekiosk.spring.api.service.order.request.OrderCreateRequest;
import com.example.cafekiosk.spring.api.service.order.response.OrderResponse;
import com.example.cafekiosk.spring.domain.product.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;
    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrder() {
        // given : 모든 준비 단계가 여기에 포함
        Product product1 = createProduct(ProductType.HAND_MADE, "001", 4000, "아메리카노");
        Product product2 = createProduct(ProductType.HAND_MADE, "002", 4500, "카페라떼");
        Product product3 = createProduct(ProductType.HAND_MADE, "003", 5000, "팥빙수");

        LocalDateTime registeredDateTime = LocalDateTime.now();

        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "002"))
                .build();

        // when : 로직, 행위만 수행
        OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

        // then : 결과를 검증
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 8500);
        assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productInfo.productNumber", "productInfo.price")
                .containsExactlyInAnyOrder(
                        tuple("001", 4000),
                        tuple("002", 4500)
                );


    }

    private Product createProduct (ProductType type, String productNumber, int price, String name) {
        return Product.builder()
                .productType(type)
                .productSellingType(ProductSellingType.SELLING)
                .productInfo(ProductInfo.builder()
                        .name(name)
                        .productNumber(productNumber)
                        .price(price)
                        .build())
                .build();
    }
}