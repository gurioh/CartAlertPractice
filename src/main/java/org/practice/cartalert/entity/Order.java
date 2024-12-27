package org.practice.cartalert.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.practice.cartalert.event.Events;
import org.practice.cartalert.event.order.OrderCreateEvent;
import org.practice.cartalert.service.dto.CartRequestDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @Column(name = "user_id", nullable = false)
    private Long userId; // 사용자 ID

    @Column(name = "commerce_order_no", length = 255)
    private String commerceOrderNo; // 상거래 주문 번호

    @Column(name = "status", length = 255)
    private String status; // 주문 상태

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount; // 총 가격

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; // 생성 시간

    @Column(name = "created_by", length = 255)
    private String createdBy; // 생성자

    public Order() {}

    public Order(Long userId, String commerceOrderNo, String status, BigDecimal totalAmount, List<OrderItem> orderItems){
        this.userId = userId;
        this.commerceOrderNo = commerceOrderNo;
        this.status = status;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
    }


    public static Order create(Long userId, String commerceOrderNo, String status, BigDecimal totalAmount, List<OrderItem> orderItems){
        Order newOrder = new Order(userId,commerceOrderNo,status,totalAmount,orderItems);
        newOrder.raiseCreatedEvent();
        return newOrder;
    }

    private void raiseCreatedEvent() {
        Events.raise(OrderCreateEvent.builder()
                .commerceOrderNo(this.commerceOrderNo)
                .totalAmount(this.totalAmount)
                .status(this.status)
                .userId(this.userId)
                .orderItems(this.orderItems)
                .build());
    }
}
