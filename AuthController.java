package com.Ecommerce.App.controller;

import com.Ecommerce.App.dto.ApiResponseDTO;
import com.Ecommerce.App.dto.LoginRequestDTO;
import com.Ecommerce.App.dto.RegisterRequestDTO;
import com.Ecommerce.App.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO request) {
        var result = authService.register(request);
        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("Registered successfully")
                .data(result)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {
        var result = authService.login(request);
        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("Login successful")
                .data(result)
                .build());
    }
}