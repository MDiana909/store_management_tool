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

/**
 * Aspect for auditing activity on user and order services.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AuditLogAspect {
    private final AuditLogJpaRepository auditLogJpaRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditLogAspect.class);

    /**
     * Pointcut to match all methods of the UserService and OrderService classes.
     */
    @Pointcut("execution(* com.dvm.store_management_tool.product_service.service.UserService.*(..)) ||" +
            "execution(* com.dvm.store_management_tool.product_service.service.OrderService.*(..))")
    public void userOperations() {}

    /**
     * Advice to run before the execution of any methods of the UserService or OrderService to log actions.
     * @param joinpoint the join point that represents the intercepted method
     */
    @Before("userOperations()")
    public void logUserActivity(final JoinPoint joinpoint) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String username = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "UNAUTHENTICATED_USER";

        final String actionTaken = joinpoint.getSignature().getName();

        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final String accessedResource = (attributes != null) ? attributes.getRequest().getRequestURI() : "";

        final Date timestamp = new Date();

        final AuditLog auditLogs = AuditLog.builder()
                        .username(username)
                        .actionTaken(actionTaken)
                        .accessedResource(accessedResource)
                        .timestamp(timestamp)
                        .build();

        LOGGER.info("[AUDIT] The user {} is accessing the method {} on {}, at {}", username, actionTaken, accessedResource, timestamp);
        auditLogJpaRepository.save(auditLogs);
    }
}
