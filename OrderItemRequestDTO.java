package com.Ecommerce.App.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {

    @NotNull(message = "Product ID required")
    private Long productId;

    @NotNull(message = "Quantity required")
    @Min(value = 1, message = "Min quantity is 1")
    private Integer quantity;
}