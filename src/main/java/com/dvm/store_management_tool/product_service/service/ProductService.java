package com.dvm.store_management_tool.product_service.service;

import com.dvm.store_management_tool.product_service.dto.product.ProductCreateRequest;
import com.dvm.store_management_tool.product_service.dto.product.ProductDto;
import com.dvm.store_management_tool.product_service.dto.product.ProductUpdateRequest;
import com.dvm.store_management_tool.product_service.entity.Product;
import com.dvm.store_management_tool.product_service.exception.ProductNotFoundException;
import com.dvm.store_management_tool.product_service.mapper.ProductDtoMapper;
import com.dvm.store_management_tool.product_service.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductJpaRepository productJpaRepository;

    public List<Product> getProductsByName(final String name) {
        List<Product> products = productJpaRepository.findByNameContainingIgnoreCase(name);

        if (products.isEmpty()) {
            log.warn("No product found with name {}", name);
            throw new ProductNotFoundException(name);
        }

        log.info("Found {} products", products.size());
        return products;
    }

    public List<Product> getAllProducts() {
        log.info("Getting all products");
        return productJpaRepository.findAll();
    }

    public Product addProduct(ProductCreateRequest request) {
        Product product = Product.builder()
                .description(request.description())
                .category(request.category())
                .price(request.price())
                .stock(request.stock())
                .name(request.name())
                .build();
        log.info("Adding product {}", product);

        return productJpaRepository.save(product);
    }

    public ProductDto updateProductPartially(final ProductUpdateRequest request, final Long id) {
        Product productToUpdate = productJpaRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        request.name().ifPresent(productToUpdate::setName);
        request.price().ifPresent(productToUpdate::setPrice);
        request.stock().ifPresent(productToUpdate::setStock);
        productJpaRepository.save(productToUpdate);

        log.info("Successfully updated product {}", productToUpdate);

        return ProductDtoMapper.mapProductToDto(productToUpdate);
    }

    public void deleteProduct(final Long id) {
        if(productJpaRepository.findById(id).isEmpty()) {
            log.warn("No product found with id {}", id);
            throw new ProductNotFoundException(id);
        }
        productJpaRepository.deleteById(id);
    }
}
