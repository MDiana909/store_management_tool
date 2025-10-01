package com.dvm.store_management_tool.product_service.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends AppException {
    public ProductNotFoundException(final String name) {
        super("The product with name " + name + " could not be found.");
    }

    public ProductNotFoundException(final Long id) {
        super("The product with id " + id + " could not be found.");
    }

    @Override
    public String getErrorCode() {
        return "ERR_PRODUCT_NOT_FOUND";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
