package com.dvm.store_management_tool.product_service.dto.user;

import com.dvm.store_management_tool.product_service.entity.Role;

public record UserDto(Long id, String username, String password, Role role) {
}
