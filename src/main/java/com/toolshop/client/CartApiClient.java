package com.toolshop.client;

import com.toolshop.dto.carts.CartItemRequestDto;
import com.toolshop.dto.user.AuthRequestDto;
import io.restassured.response.ValidatableResponse;

public class CartApiClient extends BaseClient{

    public CartApiClient(String baseUri) {
        super(baseUri);
    }

    public CartApiClient(String baseUri, String token) {
        super(baseUri, token);
    }

    private static final String ENDPOINT = "/carts";

    public ValidatableResponse createCart() {
        return postRequest(ENDPOINT)
                .then();

    }

    public ValidatableResponse addItemToCart(String id){
        return postRequest(ENDPOINT, id)
                .then();
    }
    // POST /carts/{id} — add item — public
    public ValidatableResponse addItemToCart(String cartId, CartItemRequestDto item) {
        return postPublicRequest(ENDPOINT + "/" + cartId, item)
                .then();
    }

    // POST /carts/{id} with invalid product id
    public ValidatableResponse addItemToCartInvalid(String cartId, CartItemRequestDto item) {
        return postPublicRequest(ENDPOINT + "/" + cartId, item)
                .then();
    }

    // precondition helper
    public String createCartAndGetId() {
        return createCart()
                .assertThat().statusCode(201)
                .extract()
                .path("id");
    }

    public ValidatableResponse getCart(String cartId) {
        return getByIdPublicRequest(ENDPOINT, cartId)
                .then();
    }

    public ValidatableResponse deleteCart(String cartId) {
        return deleteByIdPublicRequest(ENDPOINT, cartId)
                .then();
    }

}
