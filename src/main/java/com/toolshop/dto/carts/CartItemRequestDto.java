package com.toolshop.dto.carts;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder(toBuilder = true)
public class CartItemRequestDto {

    private String product_id;
    private int quantity;



}
