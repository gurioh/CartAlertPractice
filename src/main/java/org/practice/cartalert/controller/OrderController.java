package org.practice.cartalert.controller;


import lombok.RequiredArgsConstructor;
import org.practice.cartalert.entity.Order;
import org.practice.cartalert.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @GetMapping(value = "/list")
    public List<Order> list(@RequestParam("userId") Long userId){
        return orderService.findOrderList(userId);
    }

}
