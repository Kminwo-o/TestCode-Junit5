package com.example.cafekiosk.spring.api.service.order;

import com.example.cafekiosk.spring.api.service.order.request.OrderCreateRequest;
import com.example.cafekiosk.spring.api.service.order.response.OrderResponse;
import com.example.cafekiosk.spring.domain.order.OrderRepository;
import com.example.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import com.example.cafekiosk.spring.domain.product.*;
import com.example.cafekiosk.spring.domain.stock.Stock;
import com.example.cafekiosk.spring.domain.stock.StockRepository;
import com.example.cafekiosk.spring.exception.OrderServiceException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
//@Transactional
@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private StockRepository stockRepository;

    @AfterEach
    void tearDown() {
//        productRepository.deleteAll(); -> 일일이 하나하나 확인하며 지운다. 성능상 좋지 않음.
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    @DisplayName("주문 번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrder() {
        // given : 모든 준비 단계가 여기에 포함
        Product product1 = createProduct(ProductType.HAND_MADE, "001", 4000, "아메리카노");
        Product product2 = createProduct(ProductType.HAND_MADE, "002", 4500, "카페라떼");
        Product product3 = createProduct(ProductType.HAND_MADE, "003", 5000, "팥빙수");

        LocalDateTime registeredDateTime = LocalDateTime.now();

        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001", "002", "003"))
                .build();

        // when : 로직, 행위만 수행
        OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

        // then : 결과를 검증
        /* 주문 음료의 총 가격, 주문 음료 코드와 가격 확인 */
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 17500);
        assertThat(orderResponse.getProducts()).hasSize(4)
                .extracting("productInfo.productNumber", "productInfo.price")
                .containsExactlyInAnyOrder(
                        tuple("001", 4000),
                        tuple("001", 4000),
                        tuple("002", 4500),
                        tuple("003", 5000)
                );
    }

    @DisplayName("재고와 관련된 상품이 포함된 주문 번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrderWithStock() {
        // given : 모든 준비 단계가 여기에 포함
        Product product1 = createProduct(ProductType.BOTTEL, "001", 4000, "아메리카노");
        Product product2 = createProduct(ProductType.BAKERY, "002", 4500, "카페라떼");
        Product product3 = createProduct(ProductType.HAND_MADE, "003", 5000, "팥빙수");

        LocalDateTime registeredDateTime = LocalDateTime.now();

        productRepository.saveAll(List.of(product1, product2, product3));

        // Stock 재고.
        Stock stock1 = Stock.create("001", 2);
        Stock stock2 = Stock.create("002", 2);

        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001", "002", "003"))
                .build();

        // when : 로직, 행위만 수행
        OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

        // then : 결과를 검증
        /* 주문 음료의 총 가격, 주문 음료 코드와 가격 확인 */
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 17500);
        assertThat(orderResponse.getProducts()).hasSize(4)
                .extracting("productInfo.productNumber", "productInfo.price")
                .containsExactlyInAnyOrder(
                        tuple("001", 4000),
                        tuple("001", 4000),
                        tuple("002", 4500),
                        tuple("003", 5000)
                );

        /* 주문 음료의 재고 감소 확인 */
        List<Stock> stocks = stockRepository.findAll();
        assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        tuple("001", 0),
                        tuple("002", 1)
                );
    }

    @DisplayName("재고가 없는 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
    @Test
    void createOrderWithoutStock() {
        // given : 모든 준비 단계가 여기에 포함
        Product product1 = createProduct(ProductType.BOTTEL, "001", 4000, "아메리카노");
        Product product2 = createProduct(ProductType.BAKERY, "002", 4500, "카페라떼");
        Product product3 = createProduct(ProductType.HAND_MADE, "003", 5000, "팥빙수");

        LocalDateTime registeredDateTime = LocalDateTime.now();

        productRepository.saveAll(List.of(product1, product2, product3));

        // Stock 재고.
        Stock stock1 = Stock.create("001", 2);
        Stock stock2 = Stock.create("002", 2);

        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001", "001", "002"))
                .build();

        // when, then
        /* 남은 재고보다 많은 수의 주문 요청 */
        assertThatThrownBy(() -> orderService.createOrder(request, registeredDateTime))
                .isInstanceOf(OrderServiceException.class)
                .hasMessage("재고가 부족한 상품이 있습니다.");
    }

    @DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
    @Test
    void createOrderWithDuplicateProductNumbers() {
        // given : 모든 준비 단계가 여기에 포함
        Product product1 = createProduct(ProductType.HAND_MADE, "001", 4000, "아메리카노");
        Product product2 = createProduct(ProductType.HAND_MADE, "002", 4500, "카페라떼");
        Product product3 = createProduct(ProductType.HAND_MADE, "003", 5000, "팥빙수");

        LocalDateTime registeredDateTime = LocalDateTime.now();

        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001"))
                .build();

        // when : 로직, 행위만 수행
        OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

        // then : 결과를 검증
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 8000);
        assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productInfo.productNumber", "productInfo.price")
                .containsExactlyInAnyOrder(
                        tuple("001", 4000),
                        tuple("001", 4000)
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