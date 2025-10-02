package com.dvm.store_management_tool.controller;

import com.dvm.store_management_tool.product_service.config.SecurityConfig;
import com.dvm.store_management_tool.product_service.controller.ProductController;
import com.dvm.store_management_tool.product_service.dto.order.CreateOrderRequest;
import com.dvm.store_management_tool.product_service.dto.order_item.CreateOrderItemRequest;
import com.dvm.store_management_tool.product_service.dto.product.ProductCreateRequest;
import com.dvm.store_management_tool.product_service.entity.Category;
import com.dvm.store_management_tool.product_service.entity.Product;
import com.dvm.store_management_tool.product_service.mapper.ProductDtoMapper;
import com.dvm.store_management_tool.product_service.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.method.P;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = ProductController.class)
@Import(SecurityConfig.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final List<Product> products = new ArrayList<>();
    private static Product product1, product2;
    private static ProductCreateRequest productCreateRequest;

    @BeforeAll
    static void setup() {
        product1 = Product.builder()
                .name("Burger")
                .price(BigDecimal.valueOf(10.5))
                .stock(10)
                .category(Category.FOOD)
                .description("I'll have two number 9s, a number 9 large, a number 6 with extra dip, a number 7, two number 45s, one with cheese, and a large soda.")
                .build();

        product2 = Product.builder()
                .name("Cola")
                .price(BigDecimal.valueOf(5.5))
                .stock(15)
                .category(Category.BEVERAGE)
                .description("Bottle")
                .build();

        products.add(product1);
        products.add(product2);

        productCreateRequest = new ProductCreateRequest(product1.getName(),
                product1.getPrice(), product1.getDescription(), product1.getStock(), product1.getCategory());
    }

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    public void whenSearchByName_thenControlFlowCorrect() throws Exception {
        Mockito.when(productService.getProductsByName(Mockito.any())).thenReturn(products);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products/search?name=Burger"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].name")
                                .value(products.getFirst().getName())
                );

        Mockito.verify(productService, Mockito.times(1)).getProductsByName(Mockito.any());
    }

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    public void whenGetAllProducts_thenControlFlowCorrect() throws Exception {
        Mockito.when(productService.getAllProducts()).thenReturn(products);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].name")
                                .value(products.get(0).getName())
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[1].name")
                                .value(products.get(1).getName())
                );

        Mockito.verify(productService, Mockito.times(1)).getAllProducts();
    }

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    public void whenAddProduct_thenControlFlowCorrect() throws Exception {
        Mockito.when(productService.addProduct(Mockito.any(ProductCreateRequest.class))).thenReturn(product1);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(productCreateRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$..name")
                                .value(product1.getName())
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.price")
                                .value(product1.getPrice())
                );

        Mockito.verify(productService, Mockito.times(1)).addProduct(Mockito.any(ProductCreateRequest.class));
    }

    @Test
    public void whenCreateOrderWithoutAuthentication_thenForbidden() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(productCreateRequest)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
