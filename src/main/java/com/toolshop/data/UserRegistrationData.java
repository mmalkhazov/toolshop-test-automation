package com.toolshop.data;

import com.toolshop.models.Customer;

public class UserRegistrationData {

    public Customer defaultCustomer() {
        int i = (int) (System.currentTimeMillis() / 1000L % 3600L);
         return new Customer()
                .setFirstName("Maynard")
                .setLastName("Keenan")
                .setDateOfBirth("1964-04-17")
                .setStreet("Pima")
                .setPostCode("102")
                .setHouseNumber("1045")
                .setCity("Cottonwood")
                .setState("AZ")
                .setCountry("United States of America (the)")
                .setPhone("046723")
                .setEmail("maynard" + i + "@gmail.com")
                .setPassword("MaynardToolBand1993_");
    }
}
