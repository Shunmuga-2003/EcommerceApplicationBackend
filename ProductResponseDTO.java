package com.Ecommerce.App.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {
    private Long       id;
    private String     name;
    private String     description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String     imageUrl;
    private String     categoryName;
    private String     brand;
    private Double     rating;
    private Integer    availableStock;
}