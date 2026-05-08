package com.toolshop.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder(toBuilder = true)

public class AuthRequestDto {

    private String email;
    private String password;

}
