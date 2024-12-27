package org.practice.cartalert.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "cart_item_id", nullable = false)
    private Long cartItemId;

    @Column(name = "product_id", nullable = false)
    private Long productId; // 상품 ID

    @Column(name = "product_name")
    private String productName; // 상품명

    @Column(name = "commerce_order_line_no", length = 255)
    private String commerceOrderLineNo; // 상거래 주문 번호

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // 상품 가격

    @Column(name = "quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity; // 수량

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; // 생성 시간
}
