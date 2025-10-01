package com.dvm.store_management_tool.product_service.service;

import com.dvm.store_management_tool.product_service.dto.user.UserCreateRequest;
import com.dvm.store_management_tool.product_service.dto.user.UserUpdateRequest;
import com.dvm.store_management_tool.product_service.entity.User;
import com.dvm.store_management_tool.product_service.exception.ProductNotFoundException;
import com.dvm.store_management_tool.product_service.exception.UserAlreadyExistsException;
import com.dvm.store_management_tool.product_service.exception.UserNotFoundException;
import com.dvm.store_management_tool.product_service.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    public User getUserByUsername(String username){
        log.info("Getting user by username: {}", username);

        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public List<User> getAllUsers(){
        log.info("Getting all users");
        List<User> users = userJpaRepository.findAll();
        log.debug("Total users: {}", users.size());

        return users;
    }

    public User addUser(UserCreateRequest user) {
        if (userJpaRepository.existsByUsername(user.username())) {
            log.warn("Username {} already exists", user.username());
            throw new UserAlreadyExistsException(user.username());
        }

        User newUser = User.builder()
                .username(user.username())
                .password(user.password())
                .role(user.role()).build();

        log.info("Adding user: {}", newUser);
        User savedUser = userJpaRepository.save(newUser);
        log.info("Saved user successfully: {}", savedUser);

        return savedUser;
    }

    public User updateUserRole(UserUpdateRequest user, Long id) {
        User userToBeUpdated = userJpaRepository.getReferenceById(id);
        log.info("Updating user: {}", userToBeUpdated);
        userToBeUpdated.setRole(user.role());
        userJpaRepository.save(userToBeUpdated);
        log.info("Updated user successfully: {}", userToBeUpdated);

        return userToBeUpdated;
    }

    public void deleteUserById(Long id) {
        log.info("Deleting user with id: {}", id);
        if(!userJpaRepository.existsById(id)) {
            log.warn("User with id {} does not exist", id);
            throw new UserNotFoundException(id);
        }

        userJpaRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);

        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
