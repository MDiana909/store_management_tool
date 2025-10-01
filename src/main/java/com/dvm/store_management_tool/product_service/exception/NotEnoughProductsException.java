package com.dvm.store_management_tool.product_service.exception;

import org.springframework.http.HttpStatus;

public class NotEnoughProductsException extends AppException{
    public NotEnoughProductsException(String name, int stock, int requestedQuantity) {
        super("Not enough products for order. There were " + requestedQuantity + " requested products for " + name + " and stock " + stock + ".");
    }

    @Override
    public String getErrorCode() {
        return "ERR_NOT_ENOUGH_PRODUCTS";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
