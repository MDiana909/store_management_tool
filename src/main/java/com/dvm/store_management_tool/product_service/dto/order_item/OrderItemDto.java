package com.dvm.store_management_tool.product_service.dto.order_item;

import com.dvm.store_management_tool.product_service.dto.product.ProductDto;

import java.math.BigDecimal;

/**
 * DTO representing an order item returned as response.
 * @param id the id of the order item.
 * @param product the product that has been added to the order item.
 * @param quantity the quantity of product that has been added to the order.
 * @param totalPrice the total price of the order item (product price x quantity).
 */
public record OrderItemDto(Long id, ProductDto product, int quantity, BigDecimal totalPrice) {
}
