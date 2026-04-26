package com.Ecommerce.App.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpRequestDTO {

    @NotBlank(message = "Phone is required")
    private String phone;
}