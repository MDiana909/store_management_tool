package com.dvm.store_management_tool.controller;

import com.dvm.store_management_tool.product_service.config.SecurityConfig;
import com.dvm.store_management_tool.product_service.controller.OrderController;
import com.dvm.store_management_tool.product_service.dto.order.CreateOrderRequest;
import com.dvm.store_management_tool.product_service.dto.order_item.CreateOrderItemRequest;
import com.dvm.store_management_tool.product_service.entity.*;
import com.dvm.store_management_tool.product_service.repository.UserJpaRepository;
import com.dvm.store_management_tool.product_service.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = OrderController.class)
@Import(SecurityConfig.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private UserJpaRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final List<Order> orders = new ArrayList<>();
    private static Product product1, product2;
    private static OrderItem orderItem1, orderItem2;
    private static User user;
    private static Order order1, order2;
    private static CreateOrderRequest orderRequest;

    @BeforeAll
    static void setup() {
        product1 = Product.builder().id(1L)
            .name("Cola")
            .price(BigDecimal.valueOf(5.5))
            .stock(15)
            .category(Category.BEVERAGE)
            .description("Bottle")
            .build();

        product2 = Product.builder()
                .id(2L)
                .name("Pepsi")
                .price(BigDecimal.valueOf(6.5))
                .stock(20)
                .category(Category.BEVERAGE)
                .description("Bottle")
                .build();

        orderItem1 = OrderItem.builder()
                .id(1L)
                .quantity(10)
                .product(product1)
                .totalPrice(BigDecimal.valueOf(55))
                .build();

        orderItem2 = OrderItem.builder()
                .id(2L)
                .quantity(8)
                .product(product2)
                .totalPrice(BigDecimal.valueOf(52))
                .build();

        user = User.builder()
                .id(1L)
                .username("staff")
                .password("staff")
                .role(Role.STAFF)
                .build();

        order1 = Order.builder()
                .id(1L)
                .orderItems(List.of(orderItem1, orderItem2))
                .createdBy(user)
                .totalAmount(BigDecimal.valueOf(107))
                .build();

        order2 = Order.builder()
                .id(2L)
                .orderItems(List.of(orderItem1))
                .createdBy(user)
                .totalAmount(BigDecimal.valueOf(55))
                .build();

        orderRequest = new CreateOrderRequest(List.of(new CreateOrderItemRequest(1L, 3)));

        orders.add(order1);
        orders.add(order2);
    }

    @Test
    @WithMockUser(username = "staff", roles = {"STAFF"})
    public void whenCreateOrder_thenControlFlowCorrect() throws Exception {
        Mockito.when(orderService.createNewOrder(Mockito.any(CreateOrderRequest.class))).thenReturn(orders.get(0));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.orderItems[0].product.name")
                                .value(orders.get(0).getOrderItems().get(0).getProduct().getName())
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.orderItems[0].product.price")
                                .value(orders.get(0).getOrderItems().get(0).getProduct().getPrice())
                );

        Mockito.verify(orderService, Mockito.times(1)).createNewOrder(Mockito.any(CreateOrderRequest.class));
    }

    @Test
    @WithMockUser(username = "staff", roles = {"STAFF"})
    public void whenGetAllOrders_thenControlFlowCorrect() throws Exception {
        Mockito.when(orderService.findAllOrders()).thenReturn(orders);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].orderItems[0].product.name")
                                .value(orders.get(0).getOrderItems().get(0).getProduct().getName())
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[1].orderItems[0].product.name")
                                .value(orders.get(0).getOrderItems().get(0).getProduct().getName())
                );

        Mockito.verify(orderService, Mockito.times(1)).findAllOrders();
    }

    @Test
    public void whenCreateOrderWithoutAuthentication_thenForbidden() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "staff", roles = {"STAFF"}, value = "staff", password = "staff")
    public void whenCreateOrderWithInvalidData_thenBadRequest() throws Exception {
        CreateOrderRequest invalidOrderRequest = new CreateOrderRequest(List.of(new CreateOrderItemRequest(1L, 0)));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(invalidOrderRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
