package com.Ecommerce.App.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long                       orderId;
    private String                     status;
    private BigDecimal                 totalAmount;
    private String                     paymentMethod;
    private List<OrderItemResponseDTO> items;
    private LocalDateTime              createdAt;
}