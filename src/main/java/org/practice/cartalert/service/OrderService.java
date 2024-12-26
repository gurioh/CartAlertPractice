package org.practice.cartalert.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.practice.cartalert.entity.Order;
import org.practice.cartalert.entity.OrderItem;
import org.practice.cartalert.repository.OrderItemRepository;
import org.practice.cartalert.repository.OrderRepository;
import org.practice.cartalert.service.dto.OrderRequestDTO;
import org.practice.cartalert.service.mapper.OrderItemMapper;
import org.practice.cartalert.service.mapper.OrderMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final CartItemService cartItemService;

    public List<Order> findOrderList(Long userId){
        return orderRepository.findAllByUserId(userId);
    }

    public Order register(OrderRequestDTO requestDTO) {

        Order order = orderMapper.toEntity(requestDTO);
        orderRepository.save(order);

        List<OrderItem> orderItems = requestDTO.getOrderItems()
                                               .stream()
                                               .map( orderItemMapper::toEntity)
                                               .collect(Collectors.toList());

        AtomicInteger seq = new AtomicInteger(0);
        orderItems.forEach(item -> {
            item.setCommerceOrderLineNo(String.format("%03d", seq.incrementAndGet()));
            item.setOrderId(order.getId());
        });

        orderItemRepository.saveAll(orderItems);
        cartItemService.updateIsPurchased(order.getCommerceOrderNo(), requestDTO.getCartItemIds());
        return order;
    }


}
