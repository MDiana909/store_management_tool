package com.dvm.store_management_tool.product_service.config;

import com.dvm.store_management_tool.product_service.interceptor.OrderLoggingInterceptor;
import com.dvm.store_management_tool.product_service.interceptor.ProductLoggingInterceptor;
import com.dvm.store_management_tool.product_service.interceptor.UserLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final UserLoggingInterceptor userLoggingInterceptor;
    private final OrderLoggingInterceptor orderLoggingInterceptor;
    private final ProductLoggingInterceptor productLoggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoggingInterceptor).addPathPatterns("/api/users/**");
        registry.addInterceptor(orderLoggingInterceptor).addPathPatterns("/api/orders/**");
        registry.addInterceptor(productLoggingInterceptor).addPathPatterns("/api/products/**");
    }
}
