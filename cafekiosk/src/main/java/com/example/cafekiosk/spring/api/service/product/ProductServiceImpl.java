package com.example.cafekiosk.spring.api.service.product;

import com.example.cafekiosk.spring.api.service.product.request.ProductCreateRequest;
import com.example.cafekiosk.spring.api.service.product.response.ProductResponse;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import com.example.cafekiosk.spring.domain.product.ProductSellingType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    /*
     * readOnly = true : 읽기 전용
     * CRUD에서 CUD가 동작하지 않는다.
     * JPA : Entity 1차 캐시에 snapshot을 저장해서 변경감지를 한다. 그런데 readOnly의 경우 스냅 샷, 변경감지 X
     
     * CQRS - Command / Query를 분리하자
     * Read가 CUD보다 압도적으로 많다. 따라서 C / R을 분리함으로서 성능을 향상시키자는 것
     * 첫 번째 발걸음이 readOnly
     * */
    public List<ProductResponse> getSellingProducts() {

        List<Product> products = productRepository.findAllByProductSellingTypeIn(ProductSellingType.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .toList();
    }

    /*
    * 동시성 이슈
    * UUID
    * */
    @Override
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {

        String nextProductNumber = createNextProductNumber();

        return ProductResponse.of(productRepository.save(request.toEntity(nextProductNumber)));
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProduct();
        if (latestProductNumber == null) return "001";

        int nextProductNumberInt = Integer.parseInt(latestProductNumber) + 1;

        return String.format("%03d", nextProductNumberInt);
    }
}
