package com.Ecommerce.App.controller;

import com.Ecommerce.App.dto.ApiResponseDTO;
import com.Ecommerce.App.dto.OrderRequestDTO;
import com.Ecommerce.App.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO> placeOrder(
            @Valid @RequestBody OrderRequestDTO request) {
        var order = orderService.placeOrder(request);
        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("Order placed successfully")
                .data(order)
                .build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDTO> getOrdersByUser(
            @PathVariable Long userId) {
        var orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("Orders fetched")
                .data(orders)
                .build());
    }
}