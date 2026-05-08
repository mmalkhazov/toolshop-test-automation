package com.toolshop.dto.carts;

import com.toolshop.dto.products.ProductBrandDto;
import com.toolshop.dto.products.ProductCategoryDto;
import com.toolshop.dto.products.ProductImageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class CartItemDto {

    private String id;
    private int    quantity;
    private String cart_id;
    private String product_id;
    private CartProductDto product;


}
