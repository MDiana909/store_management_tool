package com.dvm.store_management_tool.product_service.dto.product;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Request DTO for updating a product.
 * @param name the new name of the product.
 * @param price the new price of the product.
 * @param stock the new stock of the product.
 */
public record ProductUpdateRequest (Optional<String> name, Optional<BigDecimal> price, Optional<Integer> stock) {
}
