package com.dvm.store_management_tool.product_service.service;

import com.dvm.store_management_tool.product_service.entity.Role;
import com.dvm.store_management_tool.product_service.entity.User;
import com.dvm.store_management_tool.product_service.repository.UserJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsersInitializer {

    @Bean
    public CommandLineRunner createAdminRoleUser(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            createUserIfNotExist("admin", "admin", Role.ADMIN, userJpaRepository,  passwordEncoder);
            createUserIfNotExist("manager", "manager", Role.MANAGER, userJpaRepository,  passwordEncoder);
            createUserIfNotExist("staff", "staff", Role.STAFF, userJpaRepository,  passwordEncoder);
        };
    }

    private void createUserIfNotExist(String username, String password, Role role, UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder) {
        if(userJpaRepository.findByUsername(username).isEmpty()) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(passwordEncoder.encode(password));
            newUser.setRole(role);

            userJpaRepository.save(newUser);
        }
    }
}
