package org.practice.cartalert.kafka.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.practice.cartalert.entity.OrderItem;
import org.practice.cartalert.service.dto.CartRequestDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage {

    private Long userId;
    private BigDecimal totalAmount;
    private String status;
    private String commerceOrderNo;
    private List<OrderItem> orderItems;

    public List<Long> getCartItemIds(){
        return orderItems.stream().map(OrderItem::getCartItemId).collect(Collectors.toList());
    }

}
