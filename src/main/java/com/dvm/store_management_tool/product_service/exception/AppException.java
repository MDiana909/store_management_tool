package com.dvm.store_management_tool.product_service.exception;

import org.springframework.http.HttpStatus;

public abstract class AppException extends RuntimeException {
    public AppException(final String message) {
        super(message);
    }

    public abstract String getErrorCode();
    public abstract HttpStatus getHttpStatus();
}
