package com.dvm.store_management_tool.product_service.dto.user;

import com.dvm.store_management_tool.product_service.entity.Role;

/**
 * Request DTO for updating an user.
 * @param role the new role of the user.
 */
public record UserUpdateRequest(Role role) {
}
