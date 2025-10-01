package com.dvm.store_management_tool.product_service.controller;

import com.dvm.store_management_tool.product_service.dto.user.UserCreateRequest;
import com.dvm.store_management_tool.product_service.dto.user.UserDto;
import com.dvm.store_management_tool.product_service.dto.user.UserUpdateRequest;
import com.dvm.store_management_tool.product_service.entity.User;
import com.dvm.store_management_tool.product_service.mapper.UserDtoMapper;
import com.dvm.store_management_tool.product_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        UserDto userDto = UserDtoMapper.mapUserToDto(user);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers()
                .stream()
                .map(UserDtoMapper::mapUserToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserCreateRequest request) {
        User newUser = new User();
        newUser.setUsername(request.username());
        newUser.setPassword(request.password());
        newUser.setRole(request.role());

        User savedUser = userService.addUser(newUser);
        return ResponseEntity.ok(UserDtoMapper.mapUserToDto(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUserRole(@Valid @RequestBody UserUpdateRequest request, @PathVariable Long id) {
        User user = new User();
        user.setRole(request.role());

        User updatedUser = userService.updateUserRole(user, id);
        return ResponseEntity.ok(UserDtoMapper.mapUserToDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
