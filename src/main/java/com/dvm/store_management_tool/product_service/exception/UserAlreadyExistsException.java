package com.dvm.store_management_tool.product_service.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends AppException{
    public UserAlreadyExistsException(final String username) {
        super("The user with username " + username + " already exists.");
    }

    @Override
    public String getErrorCode() {
        return "ERR_USER_ALREADY_EXISTS";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
