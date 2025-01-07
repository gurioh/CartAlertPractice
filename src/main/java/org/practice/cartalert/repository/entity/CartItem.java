package org.practice.cartalert.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id; // PK

    @Column(name = "user_id", nullable = false)
    private Long userId; // 사용자 ID

    @Column(name = "product_id", nullable = false)
    private Long productId; // 상품 ID

    @Column(name = "product_name", nullable = true)
    private String productName;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // 상품 가격

    @Column(name = "quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity; // 수량

    @Column(name = "is_purchase")
    private Boolean isPurchase; // 구매여부

    @Column(name = "commerce_order_no")
    private String commerceOrderNo; // (결제완료 후 부여)주문번호

    @CreatedDate
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; // 생성 시간

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifiedAt; // 수정 시간
}
