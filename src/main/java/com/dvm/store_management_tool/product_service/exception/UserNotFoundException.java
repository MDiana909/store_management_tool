package com.dvm.store_management_tool.product_service.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AppException{
    public UserNotFoundException(final String name) {
        super("The user with name " + name + " could not be found.");
    }

    public UserNotFoundException(final Long id) {
        super("The user with id " + id + " could not be found.");
    }
    @Override
    public String getErrorCode() {
        return "ERR_USER_NOT_FOUND";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
