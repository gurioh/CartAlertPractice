package org.practice.cartalert.repository;

import org.practice.cartalert.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> findAllByUserIdAndIsPurchaseFalse(Long userId);

    List<CartItem> findAllByIdIn(List<Long> ids);
}
