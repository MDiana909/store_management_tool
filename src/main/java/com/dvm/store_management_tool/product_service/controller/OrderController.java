package com.dvm.store_management_tool.product_service.controller;

import com.dvm.store_management_tool.product_service.dto.order.CreateOrderRequest;
import com.dvm.store_management_tool.product_service.dto.order.OrderDto;
import com.dvm.store_management_tool.product_service.entity.Order;
import com.dvm.store_management_tool.product_service.mapper.OrderDtoMapper;
import com.dvm.store_management_tool.product_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        List<OrderDto> ordersDto = orders.stream()
                .map(OrderDtoMapper::mapOrderToDto)
                .toList();

        return ResponseEntity.ok(ordersDto);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest request) {
        Order newOrder = orderService.createNewOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OrderDtoMapper.mapOrderToDto(newOrder));
    }
}
