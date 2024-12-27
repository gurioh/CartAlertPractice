package org.practice.cartalert.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventDispatcher {

    private final List<EventHandler<? extends DomainEvent>> eventHandlers;

    @EventListener
    public void dispatch(DomainEvent event) {
        log.debug("Dispatching event: {}", event.getClass().getSimpleName());

        eventHandlers.stream()
                .filter(handler -> handler.supports(event.getClass()))
                .forEach(handler -> {
                    try {
                        ((EventHandler<DomainEvent>) handler).handle(event);
                    } catch (Exception e) {
                        log.error("Error handling event: {}", event.getClass().getSimpleName(), e);
                        throw e;
                    }
                });
    }
}
