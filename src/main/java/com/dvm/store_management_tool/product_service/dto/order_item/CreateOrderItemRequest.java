package com.dvm.store_management_tool.product_service.dto.order_item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateOrderItemRequest (@NotBlank Long productId, @PositiveOrZero int quantity){
}
