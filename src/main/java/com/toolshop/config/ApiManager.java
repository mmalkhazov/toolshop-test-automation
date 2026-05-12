package com.toolshop.config;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiManager {

    private String token;

    public ApiManager startApi() {
        String baseUrl  = ConfigManager.get("base.url");
        String email    = ConfigManager.get("user.email");
        String password = ConfigManager.get("user.password");
        System.out.println("DEBUG baseUrl=[" + baseUrl + "]");
        System.out.println("DEBUG email=[" + email + "]");
        RestAssured.baseURI = baseUrl;
        this.token = given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "email",    email,
                        "password", password
                ))
                .post("/users/login")
                .then()
                .statusCode(200)
                .extract()
                .path("access_token");

        System.out.println("ApiManager: token acquired: " + token);
        return this;
    }

    public String getToken()   { return token; }
    public String getBaseUrl() { return ConfigManager.get("base.url"); }
}