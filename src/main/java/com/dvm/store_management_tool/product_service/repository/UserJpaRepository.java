package com.dvm.store_management_tool.product_service.repository;

import com.dvm.store_management_tool.product_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(final String username);
    boolean existsByUsername(final String username);
}
