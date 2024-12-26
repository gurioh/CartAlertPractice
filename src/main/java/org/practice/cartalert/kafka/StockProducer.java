package org.practice.cartalert.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.RetriableException;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "stock-events";

    public CompletableFuture<SendResult<String, Object>> sendStockMessage(StockMessage message) {
        ProducerRecord<String, Object> record = new ProducerRecord<>(
                TOPIC,
                String.valueOf(message.getStockId()),
                message
        );

        return kafkaTemplate.send(record)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        handleSuccess(result);
                    } else {
                        handleError(record, ex);
                    }
                });
    }

    private void handleSuccess(SendResult<String, Object> result) {
        log.info("Message sent successfully to partition {} at offset {}",
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().offset());
    }

    private void handleError(ProducerRecord<String, Object> record, Throwable ex) {
        String errorMessage = String.format(
                "Failed to send message to topic %s, key: %s",
                record.topic(),
                record.key()
        );

        if (ex instanceof RetriableException) {
            log.warn("Temporary error: {}", errorMessage, ex);
            throw new KafkaProducerException(record, "Retriable error occurred", ex);
        } else if (ex instanceof AuthorizationException) {
            log.error("Authorization error: {}", errorMessage, ex);
            throw new KafkaProducerException(record, "Authorization failed", ex);
        } else if (ex instanceof SerializationException) {
            log.error("Serialization error: {}", errorMessage, ex);
            throw new KafkaProducerException(record, "Serialization failed", ex);
        } else {
            log.error("Unexpected error: {}", errorMessage, ex);
            throw new KafkaProducerException(record, "Unexpected error occurred", ex);
        }
    }
}