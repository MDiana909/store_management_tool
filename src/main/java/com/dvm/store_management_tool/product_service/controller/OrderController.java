package com.dvm.store_management_tool.product_service.controller;

import com.dvm.store_management_tool.product_service.dto.order.CreateOrderRequest;
import com.dvm.store_management_tool.product_service.dto.order.OrderDto;
import com.dvm.store_management_tool.product_service.entity.Order;
import com.dvm.store_management_tool.product_service.mapper.OrderDtoMapper;
import com.dvm.store_management_tool.product_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller used for handling order API endpoints.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
    /**
     * Seervice responsible for handling order business logic.
     */
    private final OrderService orderService;

    /**
     * Retrieve the list containing all orders.
     * Returns HTTP 204 No Content if to orders are found.
     * @return a ResponseEntity containig all orders.
     */
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        final List<Order> orders = orderService.findAllOrders();
        if(orders == null || orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        final List<OrderDto> ordersDto = orders.stream()
                .map(OrderDtoMapper::mapOrderToDto)
                .toList();

        return ResponseEntity.ok(ordersDto);
    }

    /**
     * Create a new order.
     * Returns HTTP 201 Created if the order is successfully created.
     * Returns HTTP 400 Bad Request if an error occurs while creating the order.
     * @param request the request containing the required data for creating an order
     * @return a ResponseEntity containing the created order.
     */
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        final Order newOrder = orderService.createNewOrder(request);

        if(newOrder == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OrderDtoMapper.mapOrderToDto(newOrder));
    }
}
