package com.example.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /*
    * select
    * from product
    * where product_selling_type in ('SELLING', 'HOLD')
    * */
    List<Product> findAllByProductSellingTypeIn(List<ProductSellingType> sellingTypes);

    List<Product> findAllByProductInfo_ProductNumberIn(List<String> productNumbers);

    @Query(value = "select p.product_number from product p order by id desc limit 1", nativeQuery = true)
    String findLatestProduct();
}
