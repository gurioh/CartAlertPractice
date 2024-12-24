package org.practice.cartalert.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.practice.cartalert.entity.Order;
import org.practice.cartalert.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> findOrderList(Long userId){
        return orderRepository.findAllByUserId(userId);
    }
}
