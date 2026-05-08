package com.toolshop.tests.API.product;

import com.toolshop.client.ProductApiClient;
import com.toolshop.dto.products.ErrorProductsResponseDto;
import com.toolshop.dto.products.ProductDataDto;
import com.toolshop.tests.API.ApiTestBase;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("API Testing")
@Feature("Products API")
public class GetProductByIdApiTests extends ApiTestBase {

    private String validProductId;
    private static final String INVALID_Product_ID = "99999";

    @BeforeClass
    public void setUpClient() {
        productClient = new ProductApiClient(api.getBaseUrl());
        validProductId = productClient.getFirstProductId();

    }


    @Test
    @Story("Get product by valid ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that product details are returned for a valid product ID")
    public void getProductByIdPositiveTest() {
        ProductDataDto response = productClient.getProductById(validProductId)
                .assertThat().log().all().statusCode(200)
                .extract().as(ProductDataDto.class);

        Assert.assertEquals(response.getId(),   validProductId);
        Assert.assertNotNull(response.getName());
        Assert.assertTrue(response.getPrice()  > 0);
        Assert.assertNotNull(response.getCategory().getName());
        Assert.assertNotNull(response.getBrand().getName());
    }

    @Test
    @Story("Get product by invalid ID returns 404")
    @Severity(SeverityLevel.NORMAL)
    public void getProductByInvalidIdNegativeTest() {
        ErrorProductsResponseDto response= productClient.getProductById(INVALID_Product_ID)
                .assertThat().log().all().statusCode(404)
                        .extract().as(ErrorProductsResponseDto.class);

        Assert.assertEquals(response.getMessage(), "Requested item not found");
    }

}
