package com.Ecommerce.App.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDTO {
    private Long   id;
    private String name;
    private String imageUrl;
}