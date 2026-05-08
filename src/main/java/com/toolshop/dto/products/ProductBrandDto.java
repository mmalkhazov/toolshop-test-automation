package com.toolshop.dto.products;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class ProductBrandDto {

    private String id;
    private String name;
    private String slug;


}
