package org.practice.cartalert.enums;

import lombok.Getter;

@Getter
public enum StockHistoryType {
    IN("입고"),
    OUT("출고"),
    ADJUST("조정");

    private final String description;

    StockHistoryType(String description) {
        this.description = description;
    }
}