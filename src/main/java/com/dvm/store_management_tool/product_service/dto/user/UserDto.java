package com.dvm.store_management_tool.product_service.dto.user;

import com.dvm.store_management_tool.product_service.entity.Role;

/**
 * DTO representing an user returned as response.
 * @param id the id of the user
 * @param username the username for the user.
 * @param role the role of the user.
 */
public record UserDto(Long id, String username, Role role) {
}
