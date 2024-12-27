package org.practice.cartalert.event.stock;

import lombok.Getter;
import org.practice.cartalert.event.DomainEvent;

import java.time.LocalDateTime;


@Getter
public class StockIncreaseEvent implements DomainEvent {

    private final Long productId;
    private final LocalDateTime occurredAt;
    private final Integer quantity;
    private final Integer beforeQuantity;

    public StockIncreaseEvent(Long productId, Integer quantity, Integer beforeQuantity) {
        this.productId = productId;
        this.occurredAt = LocalDateTime.now();
        this.quantity = quantity;
        this.beforeQuantity = beforeQuantity;
    }

}