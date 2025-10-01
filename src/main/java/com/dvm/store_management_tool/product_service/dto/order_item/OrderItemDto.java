package com.dvm.store_management_tool.product_service.dto.order_item;

import com.dvm.store_management_tool.product_service.dto.product.ProductDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record OrderItemDto(@NotBlank Long id, @NotBlank ProductDto product, @PositiveOrZero int quantity, @PositiveOrZero BigDecimal totalPrice) {
}
