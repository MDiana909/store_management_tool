package com.dvm.store_management_tool.product_service.service;

import com.dvm.store_management_tool.product_service.dto.order.CreateOrderRequest;
import com.dvm.store_management_tool.product_service.dto.order_item.CreateOrderItemRequest;
import com.dvm.store_management_tool.product_service.entity.Order;
import com.dvm.store_management_tool.product_service.entity.OrderItem;
import com.dvm.store_management_tool.product_service.entity.Product;
import com.dvm.store_management_tool.product_service.entity.User;
import com.dvm.store_management_tool.product_service.exception.NotEnoughProductsException;
import com.dvm.store_management_tool.product_service.exception.OrderNotFoundException;
import com.dvm.store_management_tool.product_service.exception.ProductNotFoundException;
import com.dvm.store_management_tool.product_service.exception.UserNotFoundException;
import com.dvm.store_management_tool.product_service.repository.OrderJpaRepository;
import com.dvm.store_management_tool.product_service.repository.ProductJpaRepository;
import com.dvm.store_management_tool.product_service.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public Order createNewOrder(CreateOrderRequest request) {
        final User createdBy = getCurrentlyAuthenticatedUser();
        log.info("The user creating the order: {}", createdBy);

        final Order order = new Order();
        order.setCreatedBy(createdBy);
        order.setTotalAmount(BigDecimal.ZERO);

        List<OrderItem> orderItems = mapCreateOrderRequestToOrderItems(request);

        orderItems.forEach(this::updateAndValidateStock);

        orderItems.forEach(order::addOrderItem);

        order.setTotalAmount(orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        Order savedOrder = orderJpaRepository.save(order);

        log.info("Order has been successfully created: {}", order);

        return savedOrder;
    }

    public List<Order> findAllOrders() {
        log.info("Getting all Orders");
        return orderJpaRepository.findAll();
    }

    public Order findOrderById(final Long id) {
        return orderJpaRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    private User getCurrentlyAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userJpaRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("User not found with username {}", username);
            return new UserNotFoundException(username);
        });
    }

    private List<OrderItem> mapCreateOrderRequestToOrderItems(CreateOrderRequest createOrderRequest) {
        return createOrderRequest.orderItems().stream()
                .map((CreateOrderItemRequest item) ->
                        {
                            final Product product = productJpaRepository.findById(item.productId())
                                    .orElseThrow(() -> new ProductNotFoundException(item.productId()));

                            return OrderItem.builder()
                                    .product(product)
                                    .totalPrice(BigDecimal.valueOf(item.quantity()).multiply(product.getPrice()))
                                    .quantity(item.quantity())
                                    .build();
                        })
                .toList();
    }

    private void updateAndValidateStock (OrderItem orderItem) {
        Product product = orderItem.getProduct();
        int stockLeft = product.getStock() - orderItem.getQuantity();

        if(stockLeft < 0) {
            log.warn("Not enough products of type {} available. Requested: {}. Available: {}", product.getName(), orderItem.getQuantity(), product.getStock());
            throw new NotEnoughProductsException(product.getName(), product.getStock(), orderItem.getQuantity());
        }
        product.setStock(stockLeft);
        productJpaRepository.save(product);
        log.info("Stock of product {} updated. Remaining products: {}", product.getName(), product.getStock());
    }
}
