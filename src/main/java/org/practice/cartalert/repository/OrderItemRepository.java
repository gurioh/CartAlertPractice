package org.practice.cartalert.repository;

import org.practice.cartalert.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


    List<OrderItem> findAllByOrderId(Long orderId);
}
