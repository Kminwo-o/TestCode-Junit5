package com.example.cafekiosk.spring.api.service.product;

import com.example.cafekiosk.spring.api.service.product.request.ProductCreateRequest;
import com.example.cafekiosk.spring.api.service.product.response.ProductResponse;
import com.example.cafekiosk.spring.domain.product.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@ActiveProfiles("test")
@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("상품 조회 시, 판매중과 판매보류인 상품만 모두 조회한다.")
    @Test
    void getSellingProducts() {
        // given
        Product product1 = ProductCreate("001", "아메리카노", 4000, ProductType.HAND_MADE, ProductSellingType.SELLING);
        Product product2 = ProductCreate("002", "카페라떼", 4500, ProductType.HAND_MADE, ProductSellingType.HOLD);
        Product product3 = ProductCreate("003", "팥빙수", 7000, ProductType.HAND_MADE, ProductSellingType.STOP_SELLING);

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<ProductResponse> products = productService.getSellingProducts();

        // then
        assertThat(products).hasSize(2)
                // 검증하고자 하는 필드만 추출
                .extracting("productInfo.productNumber", "productInfo.name", "productSellingType")
                // 순서와 관계없이 내용을 확인
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", ProductSellingType.SELLING),
                        tuple("002", "카페라떼", ProductSellingType.HOLD)
                );
    }

    @DisplayName("신규 상품을 등록할 때, 가장 마지막 상품의 상품번호에서 1 증가한 값이다.")
    @Test
    void createProduct() {
        // given
        Product product1 = ProductCreate("001", "아메리카노", 4000, ProductType.HAND_MADE, ProductSellingType.SELLING);
        Product product2 = ProductCreate("002", "카페라떼", 4500, ProductType.HAND_MADE, ProductSellingType.HOLD);
        Product product3 = ProductCreate("003", "팥빙수", 7000, ProductType.HAND_MADE, ProductSellingType.STOP_SELLING);

        productRepository.saveAll(List.of(product1, product2, product3));

        ProductCreateRequest request = ProductCreateRequest.builder()
                .productType(ProductType.HAND_MADE)
                .productSellingType(ProductSellingType.SELLING)
                .productInfo(ProductInfo.builder()
                        .name("카푸치노")
                        .price(5000)
                        .build())
                .build();

        // when
        ProductResponse productResponse = productService.createProduct(request);

        // then
        assertThat(productResponse)
                .extracting("productInfo.productNumber", "productInfo.name", "productSellingType", "productInfo.price")
                .contains("004", request.getProductInfo().getName(), request.getProductSellingType(), request.getProductInfo().getPrice());
        
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(4)
                .extracting("productInfo.productNumber", "productInfo.name", "productSellingType")
                // 순서와 관계없이 내용을 확인
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", ProductSellingType.SELLING),
                        tuple("002", "카페라떼", ProductSellingType.HOLD),
                        tuple("003", "팥빙수", ProductSellingType.STOP_SELLING),
                        tuple("004", "카푸치노", ProductSellingType.SELLING)
                );
        
    }

    @DisplayName("신규 상품을 등록할 때, 등록된 상품이 하나도 없느 경우 신규상품을 등록하면 상품번호는 001이다.")
    @Test
    void createProductWhenProductIsEmpty() {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .productType(ProductType.HAND_MADE)
                .productSellingType(ProductSellingType.SELLING)
                .productInfo(ProductInfo.builder()
                        .name("카푸치노")
                        .price(5000)
                        .build())
                .build();

        // when
        ProductResponse productResponse = productService.createProduct(request);

        // then
        assertThat(productResponse)
                .extracting("productInfo.productNumber", "productInfo.name", "productSellingType", "productInfo.price")
                .contains("001", request.getProductInfo().getName(), request.getProductSellingType(), request.getProductInfo().getPrice());

    }

    private Product ProductCreate(String code, String name, int price, ProductType type, ProductSellingType sellingType) {
        return Product.builder()
                .productInfo(ProductInfo.builder()
                        .productNumber(code)
                        .name(name)
                        .price(price)
                        .build())
                .productType(type)
                .productSellingType(sellingType)
                .build();
    }
}