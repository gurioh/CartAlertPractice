package org.practice.cartalert.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDTO {
    private Long userId;
    private Long cartItemId;
    private Long productId;
    private Integer quantity;
    private String productName;
    private Integer price;
}