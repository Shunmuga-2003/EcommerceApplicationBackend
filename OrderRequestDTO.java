package com.Ecommerce.App.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    @NotNull(message = "User ID required")
    private Long userId;

    @NotEmpty(message = "Items cannot be empty")
    private List<OrderItemRequestDTO> items;

    @NotBlank(message = "Payment method required")
    private String paymentMethod;

    @NotBlank(message = "Delivery address required")
    private String deliveryAddress;
}