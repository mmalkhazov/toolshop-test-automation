package com.toolshop.models;

import com.toolshop.pages.BasePage;
import org.openqa.selenium.WebDriver;

public class User  {

    String email;
    String password;

    public User() {


    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
