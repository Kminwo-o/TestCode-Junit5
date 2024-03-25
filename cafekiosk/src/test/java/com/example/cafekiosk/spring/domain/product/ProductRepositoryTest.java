package com.example.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

/*
* 단위 테스트에 가까운 테스트
* Layer 별로 끊어 보았을 때, 데이터 베이스에 Access하는 로직만 가지고 있기 때문.
* */

// @DataJpaTest 와의 차이, -> DataJpaTest가 더 가볍다. Jpa 관련만 Bean 등록을 하기 때문에.
@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품정보를 조회한다.")
    @Test
    void findAllByProductSellingTypeIn() {
        // given
        Product product1 = ProductCreate("001", "아메리카노", 4000, ProductType.HAND_MADE, ProductSellingType.SELLING);
        Product product2 = ProductCreate("002", "카페라떼", 4500, ProductType.HAND_MADE, ProductSellingType.HOLD);
        Product product3 = ProductCreate("003", "팥빙수", 7000, ProductType.HAND_MADE, ProductSellingType.STOP_SELLING);

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllByProductSellingTypeIn(List.of(ProductSellingType.SELLING, ProductSellingType.HOLD));

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

    @DisplayName("상품번호 리스트로 상품을 조회한다.")
    @Test
    void findAllByProductNumberIn() {
        // given
        Product product1 = ProductCreate("001", "아메리카노", 4000, ProductType.HAND_MADE, ProductSellingType.SELLING);
        Product product2 = ProductCreate("002", "카페라떼", 4500, ProductType.HAND_MADE, ProductSellingType.HOLD);
        Product product3 = ProductCreate("003", "팥빙수", 7000, ProductType.HAND_MADE, ProductSellingType.STOP_SELLING);

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllByProductInfo_ProductNumberIn(List.of("001", "002"));

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

    @DisplayName("가장 마지막에 등록된 상품의 상품번호를 조회한다.")
    @Test
    void findLatestProduct() {
        // given
        Product product1 = ProductCreate("001", "아메리카노", 4000, ProductType.HAND_MADE, ProductSellingType.SELLING);
        Product product2 = ProductCreate("002", "카페라떼", 4500, ProductType.HAND_MADE, ProductSellingType.HOLD);
        Product product3 = ProductCreate("003", "팥빙수", 7000, ProductType.HAND_MADE, ProductSellingType.STOP_SELLING);

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        String latestProductNumber = productRepository.findLatestProduct();

        // then
        assertThat(latestProductNumber).isEqualTo(product3.getProductInfo().getProductNumber());
    }

    @DisplayName("가장 마지막에 등록된 상품의 상품번호를 조회할 때, 상품이 하나도 없는 경우 null을 반환한다.")
    @Test
    void findLatestProductWhenProductIsEmpty() {

        // when
        String latestProductNumber = productRepository.findLatestProduct();

        // then
        assertThat(latestProductNumber).isNull();
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