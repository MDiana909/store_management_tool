package com.dvm.store_management_tool.product_service.dto.user;

import com.dvm.store_management_tool.product_service.entity.Role;
import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(@NotBlank String username, @NotBlank String password, Role role) {
}
