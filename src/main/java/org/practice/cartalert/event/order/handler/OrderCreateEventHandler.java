package org.practice.cartalert.event.order.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.practice.cartalert.event.DomainEvent;
import org.practice.cartalert.event.EventHandler;
import org.practice.cartalert.event.order.OrderCreateEvent;
import org.practice.cartalert.kafka.order.OrderMessage;
import org.practice.cartalert.kafka.order.OrderProducer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.CompletableFuture;


@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreateEventHandler implements EventHandler<OrderCreateEvent> {


    private final OrderProducer orderProducer;


    @Override
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public CompletableFuture<Void> handle(OrderCreateEvent event) {
        // Kafka 메시지 발행
        return orderProducer.sendOrderMessage(OrderMessage.builder()
                .userId(event.getUserId())
                .totalAmount(event.getTotalAmount())
                .status(event.getStatus())
                .commerceOrderNo(event.getCommerceOrderNo())
                .orderItems(event.getOrderItems())
                .build())
        .thenAccept(result -> {
            log.info("Message sent successfully: {}", event.getCommerceOrderNo());
        }).exceptionally(ex -> {
            log.error("Failed to send message: {}", event.getCommerceOrderNo(), ex);
            return null;
        });
    }

    @Override
    public boolean supports(Class<? extends DomainEvent> eventType) {
        return OrderCreateEvent.class.isAssignableFrom(eventType);
    }
}
