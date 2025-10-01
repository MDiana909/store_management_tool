package com.dvm.store_management_tool.product_service.mapper;

import com.dvm.store_management_tool.product_service.dto.order.OrderDto;
import com.dvm.store_management_tool.product_service.dto.order_item.OrderItemDto;
import com.dvm.store_management_tool.product_service.entity.Order;

import java.util.List;

public class OrderDtoMapper {
    public static OrderDto mapOrderToDto(Order order) {
        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream()
                .map(item -> new OrderItemDto(
                        item.getId(),
                        ProductDtoMapper.mapProductToDto(item.getProduct()),
                        item.getQuantity(),
                        item.getTotalPrice()
                )).toList();

        return new OrderDto(order.getId(), UserDtoMapper.mapUserToDto(order.getCreatedBy()), orderItemDtos, order.getTotalAmount());
    }
}
