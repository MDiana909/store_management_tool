package com.dvm.store_management_tool.product_service.dto.order_item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateOrderItemRequest (@NotBlank Long productId, @Positive int quantity){
}
