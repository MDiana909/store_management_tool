package com.dvm.store_management_tool.product_service.exception;

import com.dvm.store_management_tool.product_service.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),
                e.getErrorCode(),
                e.getHttpStatus().value());

        return new ResponseEntity<>(errorResponse.toString(), e.getHttpStatus());
    }
}
