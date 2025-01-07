package org.practice.cartalert.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.practice.cartalert.repository.entity.Stock;
import org.practice.cartalert.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StockService {
    private final StockRepository stockRepository;

    public void increaseStock(Long stockId, int quantity, String reason) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found"));
        stock.increase(quantity,reason);
    }

    public void decreaseStock(Long stockId, int quantity, String reason) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found"));
        stock.decrease(quantity,reason);
    }
}