package com.dvm.store_management_tool.product_service.mapper;

import com.dvm.store_management_tool.product_service.dto.product.ProductDto;
import com.dvm.store_management_tool.product_service.entity.Product;

public class ProductDtoMapper {
    public static ProductDto mapProductToDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getDescription(), product.getStock(), product.getCategory());
    }
}
