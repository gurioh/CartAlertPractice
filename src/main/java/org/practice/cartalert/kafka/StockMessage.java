package org.practice.cartalert.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMessage {
    private Long stockId;
    private Integer quantity;
    private String operation; // INCREASE, DECREASE
    private String reason;
    private LocalDateTime timestamp;
}
