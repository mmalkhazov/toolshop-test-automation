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
public class ProductCategoryDto {

private String id;
private String parent_id;
private String name;
private String slug;
private List<String> sub_categories;


}
