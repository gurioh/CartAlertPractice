package org.practice.cartalert.event.stock;


import lombok.Getter;
import org.practice.cartalert.event.DomainEvent;

import java.time.LocalDateTime;

@Getter
public class StockOutEvent implements DomainEvent {

    private final Long productId;
    private final Integer quantity;
    private final LocalDateTime occurredAt;

    public StockOutEvent(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.occurredAt = LocalDateTime.now();
    }
}
