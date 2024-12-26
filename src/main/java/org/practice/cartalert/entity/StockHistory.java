package org.practice.cartalert.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.practice.cartalert.enums.StockHistoryType;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private Product product;
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "quantity_changed", nullable = false)
    private Integer quantityChanged;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockHistoryType type;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "before_quantity", nullable = false)
    private Integer beforeQuantity;

    @Column(name = "after_quantity", nullable = false)
    private Integer afterQuantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Builder
    public StockHistory(Stock stock, Long productId, Integer quantityChanged,
                        StockHistoryType type, String reason,
                        Integer beforeQuantity, Integer afterQuantity) {
        this.stock = stock;
        this.productId = productId;
        this.quantityChanged = quantityChanged;
        this.type = type;
        this.reason = reason;
        this.beforeQuantity = beforeQuantity;
        this.afterQuantity = afterQuantity;
//        this.createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
        this.createdBy = "HENRY";
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}