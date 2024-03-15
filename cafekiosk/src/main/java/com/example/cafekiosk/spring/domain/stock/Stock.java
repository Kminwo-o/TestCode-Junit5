package com.example.cafekiosk.spring.domain.stock;

import com.example.cafekiosk.spring.domain.BaseEntity;
import com.example.cafekiosk.spring.exception.OrderServiceException;
import com.example.cafekiosk.spring.exception.StockException;
import com.example.cafekiosk.spring.exception.errorcode.ErrorCode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Stock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    private int quantity;

    @Builder
    public Stock(String productNumber, int quantity) {
        this.productNumber = productNumber;
        this.quantity = quantity;
    }

    public static Stock create(String productNumber, int quantity) {
        return Stock.builder()
                .productNumber(productNumber)
                .quantity(quantity)
                .build();
    }

    public boolean isQuantityLessThan(int quantity) {
        return this.quantity < quantity;
    }

    public void deductQuantity(int quantity) {
        if (isQuantityLessThan(quantity)) {
            throw new StockException(ErrorCode.DONT_SUBTRACT_QUANTITY);
        }
        this.quantity -= quantity;
    }
}
