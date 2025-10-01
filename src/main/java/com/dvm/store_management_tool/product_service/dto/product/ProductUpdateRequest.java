package com.dvm.store_management_tool.product_service.dto.product;

import jakarta.validation.constraints.NotBlank;

public record ProductUpdateRequest (@NotBlank String name) {
}
