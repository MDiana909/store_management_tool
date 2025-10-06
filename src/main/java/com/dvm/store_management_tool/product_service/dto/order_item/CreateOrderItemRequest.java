package com.dvm.store_management_tool.product_service.dto.order_item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Request DTO for creating a new order item.
 * @param productId the id of the product to be added to the order item.
 * @param quantity the quantity of the product that is ordered.
 */
public record CreateOrderItemRequest (@NotNull Long productId, @Positive int quantity){
}
