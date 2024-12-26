package org.practice.cartalert.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    private Long userId;
    private Long cartItemId;
    private Long productId;
    private BigDecimal quantity;
    private String productName;
    private BigDecimal price;
}