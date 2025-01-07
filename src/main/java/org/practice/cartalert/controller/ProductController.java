package org.practice.cartalert.controller;


import lombok.RequiredArgsConstructor;
import org.practice.cartalert.controller.dto.ProductCreatedDTO;
import org.practice.cartalert.global.dto.response.ApiResponse;
import org.practice.cartalert.mongo.repository.entity.Product;
import org.practice.cartalert.service.ProductService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductCreatedDTO dto){
        Long productId = productService.createProduct(dto);
        return ResponseEntity.ok(ApiResponse.success(productId));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        List<Product> productList = productService.getProductList();
        return ResponseEntity.ok(ApiResponse.success(productList));
    }
}
