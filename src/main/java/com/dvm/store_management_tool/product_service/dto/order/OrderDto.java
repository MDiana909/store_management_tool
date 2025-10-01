package com.dvm.store_management_tool.product_service.dto.order;

import com.dvm.store_management_tool.product_service.dto.order_item.OrderItemDto;
import com.dvm.store_management_tool.product_service.dto.user.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.List;

public record OrderDto(@NotBlank Long id, @NotBlank UserDto createdBy, @NotBlank List<OrderItemDto> orderItems, @PositiveOrZero BigDecimal totalAmount) {
}
