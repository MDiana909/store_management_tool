package com.dvm.store_management_tool.product_service.dto.user;

import com.dvm.store_management_tool.product_service.entity.Role;
import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for creating a new user.
 * @param username the username for the user.
 * @param password the password for the user.
 * @param role the role of the user.
 */
public record UserCreateRequest(@NotBlank String username, @NotBlank String password, Role role) {
}
