package com.dvm.store_management_tool.product_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100, message = "Product name must have between 1 and 100 characters.")
    private String name;

    @NotNull
    @Min(0)
    private BigDecimal price = BigDecimal.ZERO;

    @NotNull
    @Size(max = 500, message = "Product description must have at most 500 characters")
    @Column(length = 500)
    private String description;

    @NotNull
    @Min(0)
    private int stock;

    @Enumerated(EnumType.STRING)
    private Category category;
}
