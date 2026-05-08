package com.toolshop.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseClient {

    private final String baseUri;
    private final String token;
    private static final String INVALID_TOKEN = "invalid_token_value";

    public BaseClient(String baseUri) {
        this.baseUri = baseUri;
        this.token = null;

    }

    public BaseClient(String baseUri, String token) {
        this.baseUri = baseUri;
        this.token = token;
    }


    private RequestSpecification createBaseRequest() {
        return given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token);
    }

    // ── without token (public endpoints) ──────────────────
    private RequestSpecification createPublicRequest() {
        return given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON);
    }

    private RequestSpecification createInvalidRequest() {
        return given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + INVALID_TOKEN);
    }

    protected Response postRequest(String endpoint, Object body) {
        return createBaseRequest()
                .body(body)
                .when()
                .post(endpoint);
    }

    protected Response postRequest(String endpoint) {
        return createBaseRequest()
                .when()
                .post(endpoint);
    }

    protected Response postRequest(String endpoint, String id) {
        return createBaseRequest()
                .when()
                .post(endpoint + "/" + id);
    }

    protected Response postPublicRequest(String endpoint, Object body) {
        return createPublicRequest()
                .body(body)
                .when()
                .post(endpoint);
    }

//    protected Response postPublicRequest(String endpoint, Object body) {
//        return createPublicRequest()
//                .body(body)
//                .when()
//                .post(endpoint);
//    }

    protected Response postWithInvalidTokenRequest(String endpoint, Object body) {
        return createInvalidRequest()
                .body(body)
                .when()
                .post(endpoint);
    }

    protected Response postWithInvalidTokenRequest(String endpoint) {
        return createInvalidRequest()
                .when()
                .post(endpoint);
    }


    protected Response getRequest(String endpoint, String getEndpoint) {
        return createBaseRequest()
                .when()
                .get(endpoint, getEndpoint);
    }

    protected Response getListRequest(String endpoint) {
        return createPublicRequest()
                .when()
                .get(endpoint);  // ← just /products, no /me
    }


    protected Response getPublicRequest(String endpoint, String getEndpoint) {
        return createPublicRequest()
                .when()
                .get(endpoint, getEndpoint);
    }

    protected Response getInvalidRequest(String endpoint, String getEndpoint) {
        return createInvalidRequest()
                .when()
                .get(endpoint, getEndpoint);
    }

    protected Response getByIdRequest(String endpoint, String id) {
        return createBaseRequest()
                .when()
                .get(endpoint + "/" + id);
    }

    protected Response getByIdPublicRequest(String endpoint, String id) {
        return createPublicRequest()
                .when()
                .get(endpoint + "/" + id);
    }

    protected Response getByIdWithInvalidTokenRequest(String endpoint, String id) {
        return createInvalidRequest()
                .when()
                .get(endpoint + "/" + id);
    }


    // GET with one query param
    protected Response getPublicRequestWithParam(String endpoint, String paramName, Object paramValue) {
        return createPublicRequest()
                .queryParam(paramName, paramValue)
                .when()
                .get(endpoint);
    }

    // GET with two query params
    protected Response getPublicRequestWithParams(String endpoint,
                                                  String param1, Object value1,
                                                  String param2, Object value2) {

        return createPublicRequest()
                .queryParam(param1, value1)
                .queryParam(param2, value2)
                .when()
                .get(endpoint);

    }

//
//    protected Response sendUpdateRequest(String endpoint,Object body, int id) {
//        return createBaseRequest()
//                .body(body)
//                .when()
//                .put(endpoint + "/" + id);
//    }

    protected Response sendDeleteRequest(String endpoint, String id) {
        return createBaseRequest()
                .when()
                .delete(endpoint + "/" + id);
    }

    protected Response deletePublicRequest(String endpoint) {
        return createPublicRequest()
                .when()
                .delete(endpoint);
    }



    protected Response deleteByIdRequest(String endpoint, String id) {
        return createBaseRequest()
                .when()
                .delete(endpoint + "/" + id);
    }

    protected Response deleteByIdPublicRequest(String endpoint, String id) {
        return createPublicRequest()
                .when()
                .delete(endpoint + "/" + id);
    }
}
