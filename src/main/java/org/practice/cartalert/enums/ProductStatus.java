package org.practice.cartalert.enums;

import org.practice.cartalert.global.exception.InvalidProductStatusException;

public enum ProductStatus {
    ACTIVE("판매중"),
    INACTIVE("판매중지"),
    OUT_OF_STOCK("품절"),
    DISCONTINUED("판매종료"),
    COMING_SOON("판매예정");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAvailable() {
        return this == ACTIVE || this == COMING_SOON;
    }

    public boolean canOrder() {
        return this == ACTIVE;
    }

    public static ProductStatus from(String status) {
        try {
            return ProductStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidProductStatusException("Invalid product status: " + status);
        }
    }
}
