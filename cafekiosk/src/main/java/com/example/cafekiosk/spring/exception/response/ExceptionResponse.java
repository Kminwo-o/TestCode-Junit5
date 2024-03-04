package com.example.cafekiosk.spring.exception.response;

import com.example.cafekiosk.spring.exception.OrderServiceException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ExceptionResponse {
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public static ResponseEntity<ExceptionResponse> error(OrderServiceException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ExceptionResponse.builder()
                        .httpStatus(e.getErrorCode().getHttpStatus())
                        .code(e.getErrorCode().getErrorCode())
                        .message(e.getErrorCode().getMessage())
                        .build());
    }
}
