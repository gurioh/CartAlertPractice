package org.practice.cartalert.kafka.order;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.practice.cartalert.entity.Order;
import org.practice.cartalert.entity.OrderItem;
import org.practice.cartalert.kafka.stock.StockMessage;
import org.practice.cartalert.repository.CartItemRepository;
import org.practice.cartalert.repository.OrderItemRepository;
import org.practice.cartalert.repository.OrderRepository;
import org.practice.cartalert.service.CartItemService;
import org.practice.cartalert.service.mapper.OrderItemMapper;
import org.practice.cartalert.service.mapper.OrderMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemService cartItemService;
    private final OrderMapper orderMapper;

    private OrderItemMapper orderItemMapper;

    @KafkaListener(
            topics = "order-events",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(
            @Payload OrderMessage message,
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

    private void processMessage(OrderMessage message) {
        Order order = orderMapper.toEntity(message);
        orderRepository.save(order);

        List<OrderItem> orderItems = message.getOrderItems();

        AtomicInteger seq = new AtomicInteger(0);
        orderItems.forEach(item -> {
            item.setCommerceOrderLineNo(String.format("%03d", seq.incrementAndGet()));
            item.setOrder(order);
        });

        orderItemRepository.saveAll(orderItems);

        cartItemService.updateIsPurchased(message.getCommerceOrderNo(), message.getCartItemIds());
    }


    private void handleError(OrderMessage message, Exception e) {
        // 구현할 에러 처리 전략
        // 1. 단순 로깅
        // 2. 데드 레터 큐로 전송
        // 3. 알림 발송
        // 4. 재시도 큐로 전송
    }
}
