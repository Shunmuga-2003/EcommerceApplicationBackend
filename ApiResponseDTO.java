package com.Ecommerce.App.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseDTO {
    private boolean success;
    private String  message;
    private Object  data;
}