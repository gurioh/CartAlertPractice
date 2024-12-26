package org.practice.cartalert.controller;

import lombok.RequiredArgsConstructor;
import org.practice.cartalert.entity.CartItem;
import org.practice.cartalert.service.CartItemService;
import org.practice.cartalert.service.dto.CartRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.practice.cartalert.global.dto.response.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<CartItem>>> getCartItems(@RequestParam Long userId) {
        List<CartItem> items = cartItemService.findOrderList(userId);
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CartItem>> addToCart(@RequestBody CartRequestDTO requestDTO) {
        try {
            CartItem savedCart = cartItemService.addToCart(requestDTO);
            return ResponseEntity.ok(ApiResponse.success(savedCart));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }


    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse<Long>> deleteCartItem(@PathVariable Long cartItemId) {
        return ResponseEntity.ok(ApiResponse.success(cartItemService.deleteCartItem(cartItemId)));
    }
}
