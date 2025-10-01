package com.dvm.store_management_tool.product_service.repository;

import com.dvm.store_management_tool.product_service.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogJpaRepository extends JpaRepository<AuditLog, Long> {
}
