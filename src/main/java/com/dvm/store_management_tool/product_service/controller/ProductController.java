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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller used for handling product API endpoints.
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    /**
     * Service responsible for product business logic.
     */
    private final ProductService productService;

    /**
     * Search products based on name.
     * @param name the name of the product used as filter when searching.
     * @return a ResponseEntity containing the list of products that match the name.
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchByName(
            @RequestParam("name")
            @NotBlank
            @Size(min = 1, max = 50)
            final String name) {
        final List<ProductDto> products = productService.getProductsByName(name).stream()
                .map(ProductDtoMapper::mapProductToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

    /**
     * Retrieve the list containing all products.
     * Returns HTTP 204 No Content if no products are found.
     * @return a ResponseEntity containing all products.
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        final List<ProductDto> products = productService.getAllProducts()
                .stream()
                .map(ProductDtoMapper::mapProductToDto)
                .collect(Collectors.toList());

        if(products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(products);
    }


    /**
     * Add a new product.
     * Returns HTTP 201 Created if the product is successfully created.
     * Returns HTTP 400 Bad Request if an error occurs while creating the product.
     * @param request the request containing the required data for creating a product.
     * @return a ResponseEntity containing the created product.
     */
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(
            @Valid
            @RequestBody
            final ProductCreateRequest request) {
        final Product newProduct = productService.addProduct(request);

        if(newProduct == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ProductDtoMapper.mapProductToDto(newProduct));
    }

    /**
     * Update the name, price, and/or stock of the product with the specified id.
     * @param request the request containing the required data for updating a product.
     * @param id the id of the product.
     * @return a ResponseEntity containing the updated product.
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @Valid
            @RequestBody
            final ProductUpdateRequest request,
            @PathVariable
            final Long id) {
        return ResponseEntity.ok(productService.updateProductPartially(request, id));
    }

    /**
     * Delete the product with the specified id.
     * @param id the id of the product to be deleted.
     * @return a ResponseEntity with HTTP 204 No Content if the product is deleted successfully.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable
            final Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
