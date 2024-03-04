package com.example.cafekiosk.spring.exception.errorcode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    DONT_SUBTRACT_QUANTITY(HttpStatus.BAD_REQUEST, "O-001", "재고가 부족합니다."),
    DONT_HAVE_QUANTITY(HttpStatus.BAD_REQUEST, "O-002", "재고가 부족한 상품이 있습니다.");

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
