package com.dvm.store_management_tool.service;

import com.dvm.store_management_tool.product_service.dto.order.CreateOrderRequest;
import com.dvm.store_management_tool.product_service.dto.order_item.CreateOrderItemRequest;
import com.dvm.store_management_tool.product_service.entity.*;
import com.dvm.store_management_tool.product_service.exception.NotEnoughProductsException;
import com.dvm.store_management_tool.product_service.repository.OrderJpaRepository;
import com.dvm.store_management_tool.product_service.repository.ProductJpaRepository;
import com.dvm.store_management_tool.product_service.repository.UserJpaRepository;
import com.dvm.store_management_tool.product_service.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class OrderServiceTest {

    @MockitoBean
    private OrderJpaRepository orderJpaRepository;

    @MockitoBean
    private UserJpaRepository userRepository;

    @MockitoBean
    private ProductJpaRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    private User mockUser;
    private Product mockProduct;
    private CreateOrderRequest orderRequest;

    @BeforeEach
    public void setUp() {
        mockUser = User.builder()
                .id(1L)
                .username("staff")
                .password("staff")
                .role(Role.STAFF)
                .build();

        mockProduct = Product.builder().id(1L)
                .name("Cola")
                .price(BigDecimal.valueOf(5.5))
                .stock(15)
                .category(Category.BEVERAGE)
                .description("Bottle")
                .build();
        orderRequest = new CreateOrderRequest(List.of(new CreateOrderItemRequest(1L, 3)));
    }

    @WithMockUser(username = "staff", roles = {"STAFF"})
    @Test
    public void createOrderSuccessfully() {
        Mockito.when(userRepository.findByUsername("staff")).thenReturn(Optional.of(mockUser));
        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(mockProduct));
        Mockito.when(orderJpaRepository.save(Mockito.any(Order.class))).thenAnswer(i -> i.getArgument(0));

        Order order = orderService.createNewOrder(orderRequest);

        Assertions.assertNotNull(order);
        Assertions.assertEquals(mockUser, order.getCreatedBy());
        Assertions.assertEquals(BigDecimal.valueOf(16.5), order.getTotalAmount());
        Assertions.assertEquals(1, order.getOrderItems().size());
        Mockito.verify(orderJpaRepository, Mockito.times(1)).save(Mockito.any(Order.class));
    }

    @Test
    @WithMockUser(username = "staff", roles = {"STAFF"})
    public void throwNotEnoughProductsException_whenOutOfStock() {
        CreateOrderRequest invalidOrderRequest = new CreateOrderRequest(List.of(new CreateOrderItemRequest(1L, 60)));

        Mockito.when(userRepository.findByUsername("staff")).thenReturn(Optional.of(mockUser));
        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(mockProduct));

        Assertions.assertThrows(NotEnoughProductsException.class,
                () -> orderService.createNewOrder(invalidOrderRequest));
    }

    @Test
    @WithMockUser(username = "staff", roles = {"STAFF"})
    public void testStockUpdated_whenOrderCreated() {
        Mockito.when(userRepository.findByUsername("staff")).thenReturn(Optional.of(mockUser));
        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(mockProduct));
        Mockito.when(orderJpaRepository.save(Mockito.any(Order.class))).thenAnswer(i -> i.getArgument(0));

        orderService.createNewOrder(orderRequest);

        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        Mockito.verify(productRepository).save(productArgumentCaptor.capture());

        Product product = productArgumentCaptor.getValue();

        Assertions.assertEquals(12, product.getStock());
    }
}
