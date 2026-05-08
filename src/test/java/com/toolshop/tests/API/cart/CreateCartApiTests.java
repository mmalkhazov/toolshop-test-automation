package com.toolshop.tests.API.cart;


import com.toolshop.client.CartApiClient;
import com.toolshop.dto.carts.CartResponseDto;
import com.toolshop.tests.API.ApiTestBase;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("API")
@Feature("Cart API")
public class CreateCartApiTests extends ApiTestBase {


    @BeforeClass
    public void setUpClient() {
        cartClient = new CartApiClient(api.getBaseUrl());


    }



    @Test
    @Story("Create cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a new cart can be created successfully")
    public void createCartPositiveTest() {
        CartResponseDto response = cartClient.createCart()
                .assertThat().log().all().statusCode(201)
                .extract().as(CartResponseDto.class);
        Assert.assertNotNull(response.getId());
    }


}
