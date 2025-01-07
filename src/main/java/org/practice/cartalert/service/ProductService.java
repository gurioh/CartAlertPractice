package org.practice.cartalert.service;


import lombok.RequiredArgsConstructor;
import org.practice.cartalert.controller.dto.ProductCreatedDTO;
import org.practice.cartalert.mongo.repository.ProductRepository;
import org.practice.cartalert.mongo.repository.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Long createProduct(ProductCreatedDTO dto) {


        return null;
    }

    public List<Product> getProductList() {
        return repository.findAll();
    }
}
