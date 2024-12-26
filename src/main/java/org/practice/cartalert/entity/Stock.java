package org.practice.cartalert.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.practice.cartalert.enums.StockHistoryType;
import org.practice.cartalert.event.Events;
import org.practice.cartalert.event.SafetyStockEvent;
import org.practice.cartalert.global.exception.OutOfStockException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stock")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private Product product;
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "safety_stock", nullable = false)
    private Integer safetyStock;

    @Version
    private Long version;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private List<StockHistory> histories = new ArrayList<>();

    @Builder
    public Stock(Long productId, Integer quantity, Integer safetyStock) {
        this.productId = productId;
        this.quantity = quantity;
        this.safetyStock = safetyStock;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }

    public void decrease(int quantity, String reason) {
        if (this.quantity - quantity < 0) {
            throw new OutOfStockException("재고가 부족합니다.");
        }

        int beforeQuantity = this.quantity;
        this.quantity -= quantity;

        addHistory(StockHistoryType.OUT, quantity * -1, reason, beforeQuantity);

        if (this.quantity <= this.safetyStock) {
            // 안전재고 이벤트 발행
            Events.raise(new SafetyStockEvent(this.productId, this.quantity));
//            Events.raise(new SafetyStockEvent(this.product.getId(), this.quantity));
        }
    }

    public void increase(int quantity, String reason) {
        int beforeQuantity = this.quantity;
        this.quantity += quantity;

        addHistory(StockHistoryType.IN, quantity, reason, beforeQuantity);
    }

    private void addHistory(StockHistoryType type, int quantityChanged, String reason, int beforeQuantity) {
        StockHistory history = StockHistory.builder()
                .stock(this)
//                .product(this.product)
                .productId(this.productId)
                .type(type)
                .quantityChanged(quantityChanged)
                .reason(reason)
                .beforeQuantity(beforeQuantity)
                .afterQuantity(this.quantity)
                .build();

        this.histories.add(history);
    }

    public boolean isLowStock() {
        return this.quantity <= this.safetyStock;
    }

    public boolean hasStock(int requiredQuantity) {
        return this.quantity >= requiredQuantity;
    }
}