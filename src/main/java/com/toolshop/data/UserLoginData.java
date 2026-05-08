    package com.toolshop.data;

    import com.toolshop.models.User;

    public class UserLoginData {


        public User defaultUser(){
            return new User()
                    .setEmail("admin@practicesoftwaretesting.com")
                    .setPassword("welcome01");
        }
    }
