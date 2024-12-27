package org.practice.cartalert.event.stock;

import lombok.Getter;
import org.practice.cartalert.event.DomainEvent;

import java.time.LocalDateTime;

@Getter
public class SafetyStockEvent implements DomainEvent {
    private final Long productId;
    private final int currentQuantity;
    private final LocalDateTime occurredAt;

    public SafetyStockEvent(Long productId, int currentQuantity) {
        this.productId = productId;
        this.currentQuantity = currentQuantity;
        this.occurredAt = LocalDateTime.now();
    }

}
