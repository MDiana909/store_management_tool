package com.dvm.store_management_tool.product_service.dto.product;

import com.dvm.store_management_tool.product_service.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * Request DTO for creating a new product.
 * @param name the name of the product.
 * @param price the price of the product.
 * @param description the description of the product.
 * @param stock the stock of the product.
 * @param category the category of the product.
 */
public record ProductCreateRequest (@NotBlank String name, @PositiveOrZero BigDecimal price, @NotBlank String description, @PositiveOrZero int stock, @NotNull Category category){
}
