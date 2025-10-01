package com.dvm.store_management_tool.product_service.aspect;

import com.dvm.store_management_tool.product_service.entity.AuditLog;
import com.dvm.store_management_tool.product_service.repository.AuditLogJpaRepository;
import lombok.RequiredArgsConstructor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditLogAspect {
    private final AuditLogJpaRepository auditLogJpaRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(AuditLogAspect.class);

    @Pointcut("execution(* com.dvm.store_management_tool.product_service.service.UserService.*(..))")
    public void userOperations() {}

    @Before("userOperations()")
    public void logUserActivity(JoinPoint joinpoint) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String username = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "UNAUTHENTICATED_USER";

        final String actionTaken = joinpoint.getSignature().getName();

        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final String accessedResource = (attributes != null) ? attributes.getRequest().getRequestURI() : "";

        final Date timestamp = new Date();

        AuditLog auditLogs = AuditLog.builder()
                        .username(username)
                        .actionTaken(actionTaken)
                        .accessedResource(accessedResource)
                        .timestamp(timestamp)
                        .build();

        LOGGER.info("[AUDIT] The user {} is accessing the method {} on {}, at {}", username, actionTaken, accessedResource, timestamp);
        auditLogJpaRepository.save(auditLogs);
    }
}
