package org.practice.cartalert.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderListResponseDTO {
    private String commerceOrderNo;
    private String status;
    private BigDecimal totalAmount;
    private List<OrderItemResponseDTO> orderItems;
}
