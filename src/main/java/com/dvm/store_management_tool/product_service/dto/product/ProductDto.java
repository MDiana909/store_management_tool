package com.dvm.store_management_tool.product_service.dto.product;

import com.dvm.store_management_tool.product_service.entity.Category;

import java.math.BigDecimal;

/**
 * DTO representing a product returned as response.
 * @param id the id of the product.
 * @param name the name of the product.
 * @param price the price of the product.
 * @param description the description of the product.
 * @param stock the stock of the product.
 * @param category the category of the product.
 */
public record ProductDto(Long id, String name, BigDecimal price, String description, int stock, Category category) {
}
