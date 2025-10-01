package com.dvm.store_management_tool.product_service.exception;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends AppException {

    public OrderNotFoundException(final Long id) {
        super("The order with id " + id + " could not be found.");
    }

    @Override
    public String getErrorCode() {
        return "ERR_ORDER_NOT_FOUND";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
