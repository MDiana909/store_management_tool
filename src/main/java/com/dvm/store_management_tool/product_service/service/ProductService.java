package com.dvm.store_management_tool.product_service.service;

import com.dvm.store_management_tool.product_service.dto.product.ProductCreateRequest;
import com.dvm.store_management_tool.product_service.dto.product.ProductUpdateRequest;
import com.dvm.store_management_tool.product_service.entity.Product;
import com.dvm.store_management_tool.product_service.exception.ProductNotFoundException;
import com.dvm.store_management_tool.product_service.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductJpaRepository productJpaRepository;

    public List<Product> getProductsByName(final String name) {
        List<Product> products = productJpaRepository.findByNameContainingIgnoreCase(name);

        if (products.isEmpty()) {
            throw new ProductNotFoundException(name);
        }

        return products;
    }

    public List<Product> getAllProducts() {
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

        return productJpaRepository.save(product);
    }

    public Product updateProductName(final ProductUpdateRequest request, final Long id) {
        Product productToUpdate = productJpaRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productToUpdate.setName(request.name());
        return productJpaRepository.save(productToUpdate);
    }

    public void deleteProduct(final Long id) {
        if(productJpaRepository.findById(id).isEmpty()) {
            throw new ProductNotFoundException(id);
        }
        productJpaRepository.deleteById(id);
    }
}
