package com.Ecommerce.App.controller;

import com.Ecommerce.App.dto.ApiResponseDTO;
import com.Ecommerce.App.dto.OtpRequestDTO;
import com.Ecommerce.App.dto.OtpVerifyRequestDTO;
import com.Ecommerce.App.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController {

    private final OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponseDTO> sendOtp(
            @Valid @RequestBody OtpRequestDTO request) {
        otpService.sendOtp(request.getPhone());
        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("OTP sent to " + request.getPhone())
                .data(null)
                .build());
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponseDTO> verifyOtp(
            @Valid @RequestBody OtpVerifyRequestDTO request) {
        boolean isValid = otpService.verifyOtp(
                request.getPhone(), request.getOtp());
        if (isValid) {
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("OTP verified successfully")
                    .data(null)
                    .build());
        }
        return ResponseEntity.badRequest()
                .body(ApiResponseDTO.builder()
                        .success(false)
                        .message("Invalid or expired OTP")
                        .data(null)
                        .build());
    }
}