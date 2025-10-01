package com.dvm.store_management_tool.product_service.exception;

import com.dvm.store_management_tool.product_service.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException e) {
        return createErrorResponse(e);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return createErrorResponse(e);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return createErrorResponse(e);
    }

    @ExceptionHandler(NotEnoughProductsException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughProductsException(NotEnoughProductsException e) {
        return createErrorResponse(e);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(AppException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),
                e.getErrorCode(),
                e.getHttpStatus().value());

        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }
}
