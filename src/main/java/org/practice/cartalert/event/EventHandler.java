package org.practice.cartalert.event;

import java.util.concurrent.CompletableFuture;

public interface EventHandler<T extends DomainEvent> {
    CompletableFuture<Void> handle(T event);
    boolean supports(Class<? extends DomainEvent> eventType);
}
