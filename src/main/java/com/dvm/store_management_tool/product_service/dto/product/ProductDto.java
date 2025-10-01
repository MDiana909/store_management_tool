package com.dvm.store_management_tool.product_service.dto.product;

import com.dvm.store_management_tool.product_service.entity.Category;

import java.math.BigDecimal;

public record ProductDto(Long id, String name, BigDecimal price, String description, int stock, Category category) {
}
