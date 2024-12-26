package org.practice.cartalert.service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Long userId;
    private BigDecimal totalAmount;
    private String status;
    private String commerceOrderNo;
    private List<CartRequestDTO> orderItems;

    public List<Long> getCartItemIds(){
        return orderItems.stream().map(CartRequestDTO::getCartItemId).collect(Collectors.toList());
    }

}
