package com.dvm.store_management_tool.product_service.controller;

import com.dvm.store_management_tool.product_service.dto.product.ProductCreateRequest;
import com.dvm.store_management_tool.product_service.dto.product.ProductDto;
import com.dvm.store_management_tool.product_service.dto.product.ProductUpdateRequest;
import com.dvm.store_management_tool.product_service.entity.Product;
import com.dvm.store_management_tool.product_service.mapper.ProductDtoMapper;
import com.dvm.store_management_tool.product_service.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchByName(
            @RequestParam("name")
            @NotBlank
            @Size(min = 1, max = 50)
            String name) {
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
    public ResponseEntity<ProductDto> addProduct(
            @Valid
            @RequestBody
            ProductCreateRequest request) {
        Product newProduct = productService.addProduct(request);

        return ResponseEntity.ok(ProductDtoMapper.mapProductToDto(newProduct));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @Valid
            @RequestBody
            ProductUpdateRequest request,
            @NotBlank
            @PathVariable
            Long id) {
        return ResponseEntity.ok(productService.updateProductPartially(request, id));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(
            @RequestParam
            Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
