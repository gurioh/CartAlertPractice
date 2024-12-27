package org.practice.cartalert.kafka.stock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.practice.cartalert.service.StockService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockConsumer {
    private final StockService stockService;

    @KafkaListener(
            topics = "stock-events",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(
            @Payload StockMessage message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment
    ) {
        log.info("Received message: {} from partition: {} offset: {}",
                message, partition, offset);

        try {
            processMessage(message);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
            // 에러 처리 전략에 따라 재시도 또는 DLQ로 이동
            handleError(message, e);
        }
    }

    private void processMessage(StockMessage message) {
        switch (message.getOperation()) {
            case "INCREASE":
                stockService.increaseStock(message.getStockId(), message.getQuantity(),message.getReason());
                break;
            case "DECREASE":
                stockService.decreaseStock(message.getStockId(), message.getQuantity(),message.getReason());
                break;
            default:
                throw new IllegalArgumentException("Unknown operation: " + message.getOperation());
        }
    }

    private void handleError(StockMessage message, Exception e) {
        // 구현할 에러 처리 전략
        // 1. 단순 로깅
        // 2. 데드 레터 큐로 전송
        // 3. 알림 발송
        // 4. 재시도 큐로 전송
    }
}