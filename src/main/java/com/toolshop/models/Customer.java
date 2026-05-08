package com.toolshop.models;

public class Customer {


    String firstName;
    String lastName;
    String dateOfBirth;
    String street;
    String postCode;
    String houseNumber;
    String city;
    String state;
    String country;
    String phone;
    String email;



    String password;


    public Customer() {
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Customer setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public Customer setStreet(String street) {
        this.street = street;
        return this;

    }

    public String getPostCode() {
        return postCode;
    }

    public Customer setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public Customer setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Customer setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public Customer setState(String state) {
        this.state = state;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Customer setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Customer setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Customer setPassword(String password) {
        this.password = password;
        return this;
    }
}
