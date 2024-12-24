package org.practice.cartalert.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @Column(name = "product_id", nullable = false)
    private Long productId; // 상품 ID

    @Column(name = "commerce_order_line_no", length = 255)
    private String commerceOrderLineNo; // 상거래 주문 번호

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // 상품 가격

    @Column(name = "quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity; // 수량

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; // 생성 시간
}
