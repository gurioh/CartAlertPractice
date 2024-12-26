package org.practice.cartalert.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StockEventListener {

    @EventListener
    public void handleSafetyStockEvent(SafetyStockEvent event) {
        log.warn("안전재고 알림: 상품 ID={}, 현재 수량={}",
                event.getProductId(),
                event.getCurrentQuantity()
        );
        // 여기에 알림 로직 구현 (이메일, 슬랙 등)
    }
}