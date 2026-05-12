package com.toolshop.config;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiManager {


    private static final String BASE_URL      = ConfigManager.get("base.url");
    private static final String EMAIL         = ConfigManager.get("user.email");
    private static final String PASSWORD      = ConfigManager.get("user.password");




    private String token;

    public ApiManager startApi(){
        RestAssured.baseURI= BASE_URL;
        this.token = given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "email",    EMAIL,
                        "password", PASSWORD
                ))
                .post("/users/login")
                .then()
                .statusCode(200)
                .extract()
                .path("access_token");

        System.out.println("ApiManager: token acquired: " + token);
        return this;
    }

    public String getToken()        { return token; }
    public String getBaseUrl()      { return BASE_URL; }



    }






