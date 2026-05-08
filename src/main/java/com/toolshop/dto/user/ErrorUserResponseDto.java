package com.toolshop.dto.user;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
public class ErrorUserResponseDto {

    private String message;
    private List<String> email;
    private List<String> password;
    private List<String> first_name;
    private String error;
}
