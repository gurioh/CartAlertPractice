package org.practice.cartalert.event;

import java.time.LocalDateTime;

public interface DomainEvent {
    LocalDateTime getOccurredAt();
}
