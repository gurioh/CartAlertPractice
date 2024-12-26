package org.practice.cartalert.event;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SafetyStockEvent {
    private final Long productId;
    private final int currentQuantity;
    private final LocalDateTime occurredAt;

    public SafetyStockEvent(Long productId, int currentQuantity) {
        this.productId = productId;
        this.currentQuantity = currentQuantity;
        this.occurredAt = LocalDateTime.now();
    }
}
