package org.practice.cartalert.repository;

import org.practice.cartalert.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findAllByUserId(Long userId);
}
