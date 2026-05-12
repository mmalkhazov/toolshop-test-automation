package com.toolshop.client;

import com.toolshop.dto.products.ProductResponseDto;
import io.restassured.response.ValidatableResponse;

public class ProductApiClient extends BaseClient{


    private static final String ENDPOINT = "/products";


    public ProductApiClient(String baseUri) {
        super(baseUri);
    }
    public ProductApiClient(String baseUri, String token) {
        super(baseUri, token);
    }


    public ValidatableResponse getAllProducts() {
        return getListRequest(ENDPOINT)
                .then();
    }

    public ValidatableResponse getProductById(String id) {
        return getByIdPublicRequest(ENDPOINT, id)
                .then();
    }


    public ValidatableResponse getProductsByCategory(int categoryId) {
        return getPublicRequestWithParam(ENDPOINT, "by_category_id", categoryId)
                .then();
    }


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
