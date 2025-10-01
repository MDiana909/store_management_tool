package com.dvm.store_management_tool.product_service.service;

import com.dvm.store_management_tool.product_service.dto.user.UserCreateRequest;
import com.dvm.store_management_tool.product_service.dto.user.UserUpdateRequest;
import com.dvm.store_management_tool.product_service.entity.User;
import com.dvm.store_management_tool.product_service.exception.UserAlreadyExistsException;
import com.dvm.store_management_tool.product_service.exception.UserNotFoundException;
import com.dvm.store_management_tool.product_service.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    public User getUserByUsername(String username){
        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public List<User> getAllUsers(){
        return userJpaRepository.findAll();
    }

    public User addUser(UserCreateRequest user) {
        if (userJpaRepository.existsByUsername(user.username())) {
            throw new UserAlreadyExistsException(user.username());
        }

        User newUser = User.builder()
                .username(user.username())
                .password(user.password())
                .role(user.role()).build();

        return userJpaRepository.save(newUser);
    }

    public User updateUserRole(UserUpdateRequest user, Long id) {
        User userToBeUpdated = userJpaRepository.getReferenceById(id);
        userToBeUpdated.setRole(user.role());
        userJpaRepository.save(userToBeUpdated);

        return userToBeUpdated;
    }

    public void deleteUserById(Long id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
