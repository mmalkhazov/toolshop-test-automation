package com.toolshop.tests.API.cart;

import com.toolshop.client.CartApiClient;
import com.toolshop.client.ProductApiClient;
import com.toolshop.dto.carts.CartErrorResponseDto;
import com.toolshop.dto.carts.CartGetResponseDto;
import com.toolshop.tests.API.ApiTestBase;
import com.toolshop.utils.CartDataFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("API")
@Feature("Cart API")
public class GetCartApiTests extends ApiTestBase {
    private String productId;
    private String cartId;

    @BeforeClass
    public void setUpClient() {
        cartClient = new CartApiClient(api.getBaseUrl());
        productClient = new ProductApiClient(api.getBaseUrl());
        productId     = productClient.getFirstProductId();
    }

    @BeforeMethod
    public void precondition() {
        cartId = cartClient.createCartAndGetId();
    }


    @Test
    @Story("Get empty cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a newly created cart returns empty items list")
    public void getEmptyCartPositiveTest() {
        CartGetResponseDto response = cartClient.getCart(cartId)
                .assertThat().log().all().statusCode(200)
                .extract().as(CartGetResponseDto.class);

        Assert.assertEquals(response.getId(), cartId);
        Assert.assertTrue(response.getCart_items().isEmpty());
    }


    @Test
    @Story("Get cart with item")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that cart contains item after adding product")
    public void getCartWithItemPositiveTest() {
        cartClient.addItemToCart(cartId,
                        CartDataFactory.validCartItem(productId))
                .assertThat().statusCode(200);

        CartGetResponseDto response = cartClient.getCart(cartId)
                .assertThat().log().all().statusCode(200)
                .extract().as(CartGetResponseDto.class);

        Assert.assertEquals(response.getId(), cartId);
        Assert.assertFalse(response.getCart_items().isEmpty());
        Assert.assertEquals(response.getCart_items().get(0).getQuantity(), 1);
        Assert.assertNotNull(response.getCart_items().get(0).getProduct().getName());
        Assert.assertEquals(response.getCart_items().get(0).getProduct_id(), productId);
    }


    @Test
    @Story("Get cart with invalid ID")
    @Severity(SeverityLevel.NORMAL)
    public void getCartWithInvalidIdNegativeTest() {
        CartErrorResponseDto response= cartClient.getCart("invalid-cart-id")
                .assertThat().log().all().statusCode(404).extract().as(CartErrorResponseDto.class);
        Assert.assertEquals(response.getMessage(), "Requested item not found");
    }
}
