package com.Ecommerce.App.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponseDTO {
    private Long       productId;
    private String     productName;
    private String     imageUrl;
    private Integer    quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}