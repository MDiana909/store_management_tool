package com.dvm.store_management_tool.product_service.controller;

import com.dvm.store_management_tool.product_service.dto.user.UserCreateRequest;
import com.dvm.store_management_tool.product_service.dto.user.UserDto;
import com.dvm.store_management_tool.product_service.dto.user.UserUpdateRequest;
import com.dvm.store_management_tool.product_service.entity.User;
import com.dvm.store_management_tool.product_service.mapper.UserDtoMapper;
import com.dvm.store_management_tool.product_service.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> getUserByUsername(
            @PathVariable
            @RequestParam("name")
            @NotBlank
            @Size(min = 1, max = 50)
            String username) {
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
    public ResponseEntity<UserDto> addUser(
            @Valid
            @RequestBody
            UserCreateRequest request) {
        User savedUser = userService.addUser(request);

        return ResponseEntity.ok(UserDtoMapper.mapUserToDto(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUserRole(
            @Valid
            @RequestBody
            UserUpdateRequest request,
            @NotBlank
            @PathVariable Long id) {
        User updatedUser = userService.updateUserRole(request, id);

        return ResponseEntity.ok(UserDtoMapper.mapUserToDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(
            @NotBlank
            @PathVariable
            Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
