package com.dvm.store_management_tool.product_service.controller;

import com.dvm.store_management_tool.product_service.dto.product.ProductCreateRequest;
import com.dvm.store_management_tool.product_service.dto.product.ProductDto;
import com.dvm.store_management_tool.product_service.dto.product.ProductUpdateRequest;
import com.dvm.store_management_tool.product_service.entity.Product;
import com.dvm.store_management_tool.product_service.mapper.ProductDtoMapper;
import com.dvm.store_management_tool.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    final ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchByName(@RequestParam("name") String name) {
        List<ProductDto> products = productService.getProductsByName(name).stream()
                .map(ProductDtoMapper::mapProductToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts()
                .stream()
                .map(ProductDtoMapper::mapProductToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductCreateRequest request) {
        Product product = new Product();
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setCategory(request.category());
        product.setName(request.name());

        Product newProduct = productService.addProduct(product);

        return ResponseEntity.ok(ProductDtoMapper.mapProductToDto(newProduct));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDto> updateProductName(@Valid @RequestBody ProductUpdateRequest request, @PathVariable Long id) {
        Product product = new Product();
        product.setName(request.name());

        Product updatedProduct = productService.updateProductName(product, id);
        return ResponseEntity.ok(ProductDtoMapper.mapProductToDto(updatedProduct));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
