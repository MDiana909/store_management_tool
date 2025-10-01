package com.dvm.store_management_tool.product_service.dto;

public record ErrorResponse (String message, String errorCode, int status) {
}
