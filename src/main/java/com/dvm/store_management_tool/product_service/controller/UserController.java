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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for the user API endpoints.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    /**
     * Service responsible for handling the user business logic.
     */
    private final UserService userService;

    /**
     * Retrieve the user with the specified username.
     * @param username the username to be used for searching the user.
     * @return a ResponseEntity containing the user.
     */
    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> getUserByUsername(
            @PathVariable
            @NotBlank
            @Size(min = 1, max = 50)
            final String username) {
        final User user = userService.getUserByUsername(username);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        final UserDto userDto = UserDtoMapper.mapUserToDto(user);
        return ResponseEntity.ok(userDto);
    }

    /**
     * Retrieve the list containing all users.
     * Returns HTTP 204 No Content if no users are found.
     * @return a ResponseEntity containing all users.
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        final List<UserDto> users = userService.getAllUsers()
                .stream()
                .map(UserDtoMapper::mapUserToDto)
                .collect(Collectors.toList());

        if(users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(users);
    }

    /**
     * Add a new user.
     * Returns HTTP 201 Created if the user is successfully created.
     * Returns HTTP 400 Bad Request if an error occurs while creating the user.
     * @param request the request containing the required data for creating a user.
     * @return a ResponseEntity containing the created user.
     */
    @PostMapping
    public ResponseEntity<UserDto> addUser(
            @Valid
            @RequestBody
            final UserCreateRequest request) {
        final User savedUser = userService.addUser(request);

        if(savedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(UserDtoMapper.mapUserToDto(savedUser));
    }

    /**
     * Update the role of the user with the specified id.
     * @param request the request containing the required data for updating the role of the user.
     * @param id the id of the user.
     * @return a ResponseEntity containing the updated user with the new role.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUserRole(
            @Valid
            @RequestBody
            final UserUpdateRequest request,
            @PathVariable final Long id) {
        final User updatedUser = userService.updateUserRole(request, id);

        return ResponseEntity.ok(UserDtoMapper.mapUserToDto(updatedUser));
    }

    /**
     * Delete the user with the specified id.
     * @param id the id of the user to be deleted.
     * @return a ResponseEntity with HTTP 204 No Content if the user is deleted successfully.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(
            @PathVariable
            final Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
