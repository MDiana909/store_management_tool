package com.dvm.store_management_tool.product_service.service;

import com.dvm.store_management_tool.product_service.dto.order.CreateOrderRequest;
import com.dvm.store_management_tool.product_service.entity.Order;
import com.dvm.store_management_tool.product_service.entity.OrderItem;
import com.dvm.store_management_tool.product_service.entity.Product;
import com.dvm.store_management_tool.product_service.entity.User;
import com.dvm.store_management_tool.product_service.exception.OrderNotFoundException;
import com.dvm.store_management_tool.product_service.exception.ProductNotFoundException;
import com.dvm.store_management_tool.product_service.exception.UserNotFoundException;
import com.dvm.store_management_tool.product_service.mapper.OrderItemDtoMapper;
import com.dvm.store_management_tool.product_service.repository.OrderJpaRepository;
import com.dvm.store_management_tool.product_service.repository.ProductJpaRepository;
import com.dvm.store_management_tool.product_service.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public Order createNewOrder(CreateOrderRequest request) {
        Order order = new Order();
        User createdBy = userJpaRepository.findById(request.createdById()).orElseThrow(() -> new UserNotFoundException(request.createdById()));
        order.setCreatedBy(createdBy);

        List<OrderItem> orderItems = request.orderItems().stream()
                        .map(item ->
                                OrderItemDtoMapper.mapCreateRequestDtoToOrderItem(item, productJpaRepository.getReferenceById(item.productId())))
                .toList();

        for(OrderItem orderItem : orderItems) {
            Product product = productJpaRepository.findById(orderItem.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException(orderItem.getProduct().getId()));

            OrderItem item = OrderItem.builder()
                    .product(product)
                    .totalPrice(BigDecimal.valueOf(orderItem.getQuantity()).multiply(product.getPrice()))
                    .quantity(orderItem.getQuantity())
                    .build();

            order.addOrderItem(item);
            order.setTotalAmount(order.getTotalAmount().add(item.getTotalPrice()));
        }
        return orderJpaRepository.save(order);
    }

    public List<Order> findAllOrders() {
        return orderJpaRepository.findAll();
    }

    public Order findOrderById(final Long id) {
        return orderJpaRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}
