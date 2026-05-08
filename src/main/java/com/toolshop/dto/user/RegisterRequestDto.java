package com.toolshop.dto.user;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@Builder(toBuilder = true)
public class RegisterRequestDto {

    private String first_name;
    private String last_name;
    private List<AddressDto> address;
    private String dob;
    private String password;
    private String email;



}
