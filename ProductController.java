package com.Ecommerce.App.controller;

import com.Ecommerce.App.dto.ApiResponseDTO;
import com.Ecommerce.App.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO> getAllProducts() {
        var products = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("Products fetched")
                .data(products)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> getProductById(
            @PathVariable Long id) {
        var product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("Product fetched")
                .data(product)
                .build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponseDTO> searchProducts(
            @RequestParam String name) {
        var products = productService.searchProducts(name);
        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("Search results")
                .data(products)
                .build());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponseDTO> getByCategory(
            @PathVariable Long categoryId) {
        var products = productService
                .getProductsByCategory(categoryId);
        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("Products by category")
                .data(products)
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO> addProduct(
            @RequestParam String        name,
            @RequestParam String        description,
            @RequestParam BigDecimal    price,
            @RequestParam BigDecimal    discountPrice,
            @RequestParam Long          categoryId,
            @RequestParam String        brand,
            @RequestParam Integer       quantity,
            @RequestParam MultipartFile image)
            throws IOException {
        var product = productService.addProduct(
                name, description, price,
                discountPrice, categoryId,
                brand, quantity, image);
        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("Product added")
                .data(product)
                .build());
    }
}