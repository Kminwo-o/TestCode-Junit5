package com.example.cafekiosk.spring.exception;

import com.example.cafekiosk.spring.exception.errorcode.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderServiceException extends RuntimeException{

    private ErrorCode errorCode;

    @Builder
    public OrderServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
