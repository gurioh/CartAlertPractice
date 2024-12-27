package org.practice.cartalert.event;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface EventHandler<T extends DomainEvent> {
    CompletableFuture<Void> handle(T event);
    boolean supports(Class<? extends DomainEvent> eventType);
}
