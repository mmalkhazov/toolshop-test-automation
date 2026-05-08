package com.toolshop.utils;

import com.toolshop.dto.carts.CartItemRequestDto;

public class CartDataFactory {
    public static CartItemRequestDto validCartItem(String productId) {
        return CartItemRequestDto.builder()
                .product_id(productId)
                .quantity(1)
                .build();
    }

    public static CartItemRequestDto cartItemWithoutProductId() {
        return CartItemRequestDto.builder()
                .product_id("")
                .quantity(1)
                .build();
    }

    public static CartItemRequestDto cartItemWithZeroQuantity(String productId) {
        return CartItemRequestDto.builder()
                .product_id(productId)
                .quantity(0)
                .build();
    }
}
