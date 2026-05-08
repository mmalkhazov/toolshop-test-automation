package com.toolshop.utils;

import com.toolshop.dto.user.AddressDto;
import com.toolshop.dto.user.AuthRequestDto;
import com.toolshop.dto.user.RegisterRequestDto;
import com.toolshop.models.Customer;

import java.util.Arrays;
import java.util.List;

public class TestDataFactory {


    public static RegisterRequestDto createValidUserRequest() {
        int i = (int) (System.currentTimeMillis() / 1000L % 3600L);
        return RegisterRequestDto.builder()
                .first_name("Maynard")
                .last_name("Keenan")
                .address(createDefaultAddress())
                .dob("1964-04-17")
                .password("MaynardToolBand1993_")
                .email("maynard" + i + "@gmail.com")
                .build();
    }

    public static RegisterRequestDto createAdminUserRequest() {
        int i = (int) (System.currentTimeMillis() / 1000L % 3600L);
        return RegisterRequestDto.builder()
                .first_name("Maynard")
                .last_name("Keenan")
                .address(createDefaultAddress())
                .dob("1964-04-17")
                .password("MaynardToolBand1993_")
                .email("maynard" + i + "@gmail.com")
                .build();
    }


    public static List<AddressDto> createDefaultAddress() {
        return Arrays.asList(
                AddressDto.builder()
                        .street("Pima")
                        .city("Cottonwood")
                        .state("AZ")
                        .country("The United States of America")
                        .postalCode("046723")
                        .build()
        );
    }

    public static RegisterRequestDto userWithoutEmail() {
        return createValidUserRequest().toBuilder()
                .email("")
                .build();
    }

    public static RegisterRequestDto userWithoutPassword() {
        return createValidUserRequest().toBuilder()
                .password("")
                .build();
    }

    public static RegisterRequestDto userWithoutFirstName() {
        return createValidUserRequest().toBuilder()
                .first_name("")
                .build();
    }


    public static RegisterRequestDto userWithInvalidEmail() {
        return createValidUserRequest().toBuilder()
                .email("invalid.com")
                .build();
    }

    public static RegisterRequestDto userWithWeakPassword() {
        return createValidUserRequest().toBuilder()
                .password("weak")
                .build();
    }


   public static AuthRequestDto validAdmin(){
        return AuthRequestDto.builder()
                .email("admin@practicesoftwaretesting.com")
                .password("welcome01")
                .build();
   }

   public static AuthRequestDto adminWithWrongPassword(){
        return validAdmin().toBuilder()
                .password("wrongpassword")
                .build();
   }

   public static AuthRequestDto adminWithWrongEmail(){
        return validAdmin().toBuilder()
                .email("wrongpassword")
                .build();
   }

    public static AuthRequestDto adminWithEmptyEmail() {
        return validAdmin().toBuilder()
                .email("")
                .build();
    }

    public static AuthRequestDto adminWithEmptyPassword() {
        return validAdmin().toBuilder()
                .password("")
                .build();
    }

    public static AuthRequestDto adminWithInvalidEmailFormat() {
        return validAdmin().toBuilder()
                .email("notanemail")
                .build();
    }

}
