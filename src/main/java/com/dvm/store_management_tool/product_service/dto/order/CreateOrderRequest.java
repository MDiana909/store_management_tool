package com.dvm.store_management_tool.product_service.dto.order;

import com.dvm.store_management_tool.product_service.dto.order_item.CreateOrderItemRequest;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateOrderRequest(@NotBlank List<CreateOrderItemRequest> orderItems) {
}
