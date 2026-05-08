package com.toolshop.dto.carts;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class CartProductDto {

    private String  id;
    private String  name;
    private float   price;
    private boolean in_stock;
}
