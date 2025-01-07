package org.practice.cartalert.event.order;

import lombok.Builder;
import lombok.Getter;
import org.practice.cartalert.repository.entity.OrderItem;
import org.practice.cartalert.event.DomainEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderCreateEvent implements DomainEvent {

    private Long userId; // 사용자 ID
    private String commerceOrderNo; // 상거래 주문 번호
    private String status; // 주문 상태
    private BigDecimal totalAmount; // 총 가격
    private List<OrderItem> orderItems;
    private LocalDateTime occurredAt; //


    @Builder
    public OrderCreateEvent(Long userId, String commerceOrderNo, String status, BigDecimal totalAmount, List<OrderItem> orderItems) {
        this.userId = userId;
        this.commerceOrderNo = commerceOrderNo;
        this.status = status;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
        this.occurredAt = LocalDateTime.now();
    }
}
