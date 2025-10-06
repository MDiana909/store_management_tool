package com.dvm.store_management_tool.product_service.dto.order;

import com.dvm.store_management_tool.product_service.dto.order_item.OrderItemDto;
import com.dvm.store_management_tool.product_service.dto.user.UserDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO representing an order returned as response.
 * @param id the id of the order.
 * @param createdBy the user(staff) that created the order.
 * @param orderItems the list of items that have been added to the order.
 * @param totalAmount the total price of the order.
 */
public record OrderDto(Long id, UserDto createdBy, List<OrderItemDto> orderItems, BigDecimal totalAmount) {
}
