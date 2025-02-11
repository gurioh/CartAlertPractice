package org.practice.cartalert.repository;

import org.practice.cartalert.repository.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
