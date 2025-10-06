package com.dvm.store_management_tool.product_service.mapper;

import com.dvm.store_management_tool.product_service.dto.order_item.OrderItemDto;
import com.dvm.store_management_tool.product_service.entity.OrderItem;
import com.dvm.store_management_tool.product_service.entity.Product;

import java.math.BigDecimal;

public class OrderItemDtoMapper {
    public static OrderItemDto mapProductToDto(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getId(),
                ProductDtoMapper.mapProductToDto(orderItem.getProduct()),
                orderItem.getQuantity(),
                orderItem.getTotalPrice());
    }

    public static OrderItem mapCreateRequestDtoToOrderItem(final int quantity, final Product product) {
        return OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .totalPrice(BigDecimal.valueOf(quantity).multiply(product.getPrice()))
                .build();
    }
}
