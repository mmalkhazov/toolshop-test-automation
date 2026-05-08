package com.toolshop.dto.carts;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
public class CartGetResponseDto {

    private String id;
    private List<CartItemDto> cart_items;


}
