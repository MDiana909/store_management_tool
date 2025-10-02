package com.dvm.store_management_tool.product_service.dto;

/**
 * DTO for returning error details as response.
 * @param message the message of the error
 * @param errorCode the error code for the error type
 * @param status the HTTP status code
 */
public record ErrorResponse (String message, String errorCode, int status) {
}
