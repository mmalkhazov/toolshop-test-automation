package com.toolshop.client;

import com.toolshop.dto.user.AuthRequestDto;
import com.toolshop.dto.user.RegisterRequestDto;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class UserApiClient extends BaseClient {

    private static final String ENDPOINT = "/users";
    private static final String GET_ENDPOINT = "/me";

    public UserApiClient(String baseUri, String token) {
        super(baseUri, token);
    }


    public ValidatableResponse loginUser(AuthRequestDto authRequest) {
       return postRequest(ENDPOINT + "/login", authRequest)
        .then();


    }

    // POST /users/register 🔒
    // scratch: given().baseUri(...).header("Authorization","Bearer "+token).body(...).post("/users/register")

    public ValidatableResponse registerUser(RegisterRequestDto registerRequest) {
        return postRequest(ENDPOINT + "/register", registerRequest)
                .then();

    }

    public ValidatableResponse registerInvalidUser(RegisterRequestDto registerRequest) {
        return postWithInvalidTokenRequest(ENDPOINT + "/register", registerRequest)
        .then();

    }


    // GET /users/{id} 🔒
    // scratch: given().header("Authorization","Bearer "+token).get("/users/1")
    public ValidatableResponse getUserById(String id) {
        return getByIdRequest(ENDPOINT, id)
       .then();
    }

    public ValidatableResponse getUser(){
        return getRequest(ENDPOINT,GET_ENDPOINT)
                .then();
    }

    public ValidatableResponse getInvalidUser(){
        return getInvalidRequest(ENDPOINT,GET_ENDPOINT)
                .then();
    }
//

    public ValidatableResponse getUserByIdInvalid(String id) {
        Response response = getByIdWithInvalidTokenRequest(ENDPOINT, id);
        return response.then();
    }


//explain
public String registerAndGetId(RegisterRequestDto request) {
    return registerUser(request)
            .assertThat().statusCode(201)
            .extract()
            .path("id");
}

    //explain
    public String loginAndGetToken(String email, String password) {
        return loginUser(AuthRequestDto.builder()
                .email(email)
                .password(password)
                .build())
                .assertThat().statusCode(200)
                .extract()
                .path("access_token");
    }

    public ValidatableResponse deleteUserById(String id) {
        return sendDeleteRequest(ENDPOINT,id)
                .then();
    }
}
