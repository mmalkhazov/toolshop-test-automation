package com.toolshop.client;

import com.toolshop.dto.products.ProductResponseDto;
import io.restassured.response.ValidatableResponse;

public class ProductApiClient extends BaseClient{


    private static final String ENDPOINT = "/products";
//    private static final String GET_ENDPOINT = "/me";

    public ProductApiClient(String baseUri) {
        super(baseUri);
    }
    public ProductApiClient(String baseUri, String token) {
        super(baseUri, token);
    }

    // GET /products — no lock, public
    public ValidatableResponse getAllProducts() {
        return getListRequest(ENDPOINT)
                .then();
    }

    // GET /products/{id} — no lock, public
    public ValidatableResponse getProductById(String id) {
        return getByIdPublicRequest(ENDPOINT, id)
                .then();
    }

    // GET /products?by_category_id={id}
    public ValidatableResponse getProductsByCategory(int categoryId) {
        return getPublicRequestWithParam(ENDPOINT, "by_category_id", categoryId)
                .then();
    }

    // GET /products?sort={sort}&order={order}
    public ValidatableResponse getProductsSorted(String sort, String order) {
        return getPublicRequestWithParams(ENDPOINT,
                "sort",  sort,
                "order", order)
                .then();
    }

    public String getFirstProductId() {
        return getAllProducts()
                .assertThat().statusCode(200)
                .extract()
                .as(ProductResponseDto.class)
                .getData()
                .get(0)
                .getId();
    }

    public ValidatableResponse postProducts() {
        return postPublicRequest(ENDPOINT, "{}")
                .then();
    }

    public ValidatableResponse deleteAllProducts() {
        return deletePublicRequest(ENDPOINT)
                .then();
    }

    public ValidatableResponse deleteByIdProduct(String id) {
        return deleteByIdPublicRequest(ENDPOINT, id)
                .then();
    }
    public ValidatableResponse getSearchProducts(String query) {
        return getPublicRequestWithParam(ENDPOINT + "/search", "q", query)
                .then(

                );
    }
    public ValidatableResponse getSearchProductsNoParam() {
        return getPublicRequest(ENDPOINT + "/search","")
                .then();
    }
}
