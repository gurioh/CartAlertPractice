package org.practice.cartalert.event.stock.handler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.practice.cartalert.event.DomainEvent;
import org.practice.cartalert.event.EventHandler;
import org.practice.cartalert.event.stock.SafetyStockEvent;
import org.practice.cartalert.event.stock.StockIncreaseEvent;
import org.practice.cartalert.kafka.stock.StockMessage;
import org.practice.cartalert.kafka.stock.StockProducer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockIncreaseEventHandler implements EventHandler<StockIncreaseEvent> {

    private final StockProducer stockProducer;


    @Async
    @Override
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public CompletableFuture<Void> handle(StockIncreaseEvent event) {
        // Kafka 메시지 발행
        return stockProducer.sendStockMessage(StockMessage.builder()
                .stockId(event.getProductId())
                .quantity(event.getQuantity())
                .operation("INCREASE")
                .reason("Test")
                .timestamp(LocalDateTime.now())
                .build())
        .thenAccept(result -> {
            log.info("Message sent successfully: {}", event.getProductId());
        }).exceptionally(ex -> {
            log.error("Failed to send message: {}", event.getProductId(), ex);
            return null;
        });
    }

    @Override
    public boolean supports(Class<? extends DomainEvent> eventType) {
        return SafetyStockEvent.class.isAssignableFrom(eventType);
    }
}
