package com.Ecommerce.App.controller;

import com.Ecommerce.App.dto.ApiResponseDTO;
import com.Ecommerce.App.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO> getAllCategories() {
        var categories = categoryService.getAllCategories();
        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("Categories fetched")
                .data(categories)
                .build());
    }
}