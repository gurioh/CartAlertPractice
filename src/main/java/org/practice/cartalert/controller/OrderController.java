package org.practice.cartalert.controller;


import lombok.RequiredArgsConstructor;
import org.practice.cartalert.repository.entity.Order;
import org.practice.cartalert.service.OrderService;
import org.practice.cartalert.service.dto.OrderListResponseDTO;
import org.practice.cartalert.service.dto.OrderRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value ="/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(value = "/list")
    public ResponseEntity<?> list(@RequestParam("userId") Long userId) {
        try {
            List<OrderListResponseDTO> orderListDtos = orderService.findOrderList(userId);
            return ResponseEntity.ok(orderListDtos);
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("주문 목록을 가져오는데 실패했습니다.");
        }
    }

    @PostMapping(value ="/register")
    public ResponseEntity<?> register(@RequestBody OrderRequestDTO requestDTO){
        try {
            Order order = orderService.register(requestDTO);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("구매를 실패했습니다.");
        }
    }

}
