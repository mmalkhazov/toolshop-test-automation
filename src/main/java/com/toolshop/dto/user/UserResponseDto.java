package com.toolshop.dto.user;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class UserResponseDto {

    private String first_name;
    private String last_name;
    private AddressDto address;
    private String phone;
    private String dob;
    private String email;
    private String id;
    private String provider;
    private boolean totp_enabled;
    private boolean enabled;
    private int failed_login_attempts;
    private String created_at;



}
