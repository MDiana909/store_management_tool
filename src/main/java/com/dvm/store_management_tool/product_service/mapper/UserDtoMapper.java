package com.dvm.store_management_tool.product_service.mapper;

import com.dvm.store_management_tool.product_service.dto.user.UserDto;
import com.dvm.store_management_tool.product_service.entity.User;

public class UserDtoMapper {
    public static UserDto mapUserToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getRole());
    }
}
