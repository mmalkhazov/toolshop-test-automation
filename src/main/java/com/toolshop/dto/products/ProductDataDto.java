package com.toolshop.dto.products;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
public class ProductDataDto {


    private String id;
    private String name;
    private String description;
    private double price;
    private boolean is_location_offer;
    private boolean is_rental;
    private boolean in_stock;
    private String co2_rating;
    private boolean is_eco_friendly;
    private ProductBrandDto brand;
    private ProductCategoryDto category;
    private ProductImageDto product_image;



}
