package com.dvm.store_management_tool.product_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters.")
    private String username;

    @NotNull
    private String actionTaken;

    @NotNull
    private Date timestamp;

    @NotNull
    private String accessedResource;
}
