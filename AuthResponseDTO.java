package com.Ecommerce.App.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDTO {
    private String token;
    private Long   userId;
    private String name;
    private String email;
    private String role;
}