package com.example.cafekiosk.spring.exception;

import com.example.cafekiosk.spring.domain.stock.Stock;
import com.example.cafekiosk.spring.exception.errorcode.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StockException extends RuntimeException {
    private ErrorCode errorCode;

    @Builder
    public StockException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
