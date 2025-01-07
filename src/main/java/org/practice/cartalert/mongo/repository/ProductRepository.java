package org.practice.cartalert.mongo.repository;

import org.practice.cartalert.mongo.repository.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
