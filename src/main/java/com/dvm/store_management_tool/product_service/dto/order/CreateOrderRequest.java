package com.dvm.store_management_tool.product_service.dto.order;

import com.dvm.store_management_tool.product_service.dto.order_item.CreateOrderItemRequest;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * Request DTO for creating a new order.
 * @param orderItems the list of items to be added in the order. Contains product id and quantity.
 */
public record CreateOrderRequest(List<CreateOrderItemRequest> orderItems) {
}
