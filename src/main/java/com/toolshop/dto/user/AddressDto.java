package com.toolshop.dto.user;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder  (toBuilder = true)
public class AddressDto {

    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;




}
