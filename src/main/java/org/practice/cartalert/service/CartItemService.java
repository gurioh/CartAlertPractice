package org.practice.cartalert.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.practice.cartalert.repository.entity.CartItem;
import org.practice.cartalert.repository.CartItemRepository;
import org.practice.cartalert.service.mapper.CartItemMapper;
import org.practice.cartalert.service.dto.CartRequestDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public CartItem addToCart(CartRequestDTO requestDTO) {
        // DTO를 엔티티로 변환
        CartItem cart = cartItemMapper.toEntity(requestDTO);
        cart.setIsPurchase(false);
        // 저장
        return cartItemRepository.save(cart);
    }

    @Transactional
    public void updateIsPurchased(String commerceOrderNo, List<Long> ids){
        List<CartItem> cartItems = cartItemRepository.findAllByIdIn(ids);

        cartItems.forEach( i -> {
            i.setIsPurchase(true);
            i.setCommerceOrderNo(commerceOrderNo);
        });
    }

    public List<CartItem> findOrderList(Long userId){
        return cartItemRepository.findAllByUserIdAndIsPurchaseFalse(userId);
    }

    public Long deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
        return cartItemId;
    }
}
