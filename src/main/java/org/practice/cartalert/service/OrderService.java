package org.practice.cartalert.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.practice.cartalert.entity.Order;
import org.practice.cartalert.entity.OrderItem;
import org.practice.cartalert.repository.OrderItemRepository;
import org.practice.cartalert.repository.OrderRepository;
import org.practice.cartalert.service.dto.OrderItemResponseDTO;
import org.practice.cartalert.service.dto.OrderListResponseDTO;
import org.practice.cartalert.service.dto.OrderRequestDTO;
import org.practice.cartalert.service.mapper.OrderItemMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderService {

//    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final CartItemService cartItemService;

    public List<OrderListResponseDTO> findOrderList(Long userId){

        List<Order> orders = orderRepository.findAllByUserId(userId);

        return orders.stream()
                .map( ord -> {
                    List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(ord.getId());

                    return OrderListResponseDTO.builder()
                            .commerceOrderNo(ord.getCommerceOrderNo())
                            .status(ord.getStatus())
                            .totalAmount(ord.getTotalAmount())
                            .orderItems(orderItems.stream()
                                    .map( ordi -> OrderItemResponseDTO.builder()
                                            .price(ordi.getPrice())
                                            .quantity(ordi.getQuantity())
                                            .productId(ordi.getProductId())
                                            .productName(ordi.getProductName())
                                            .cartItemId(ordi.getCartItemId())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build();
                })
                .collect(Collectors.toList());
    }

    public Order register(OrderRequestDTO requestDTO) {

        List<OrderItem> orderItems = requestDTO.getOrderItems()
                                               .stream()
                                               .map( orderItemMapper::toEntity)
                                               .collect(Collectors.toList());

        Order newOrder = Order.create(
                requestDTO.getUserId(),
                requestDTO.getCommerceOrderNo(),
                requestDTO.getStatus(),
                requestDTO.getTotalAmount(),
                orderItems);

        return newOrder;
    }


}
