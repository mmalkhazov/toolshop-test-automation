package com.toolshop.dto.products;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class ProductImageDto {
    private String by_name;
    private String by_url;
    private String source_name;
    private String source_url;
    private String file_name;
    private String title;
    private String id;


}
