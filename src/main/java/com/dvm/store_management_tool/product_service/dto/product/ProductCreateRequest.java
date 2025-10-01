package com.dvm.store_management_tool.product_service.dto.product;

import com.dvm.store_management_tool.product_service.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductCreateRequest  (@NotBlank String name, @PositiveOrZero BigDecimal price, @NotBlank String description, @PositiveOrZero int stock, Category category){
}
