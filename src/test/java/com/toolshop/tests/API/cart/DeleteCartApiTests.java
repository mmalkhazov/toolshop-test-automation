package com.toolshop.tests.API.cart;

import com.toolshop.client.CartApiClient;
import com.toolshop.client.ProductApiClient;
import com.toolshop.tests.API.ApiTestBase;
import com.toolshop.utils.CartDataFactory;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("API")
@Feature("Cart API")
public class DeleteCartApiTests extends ApiTestBase {
    private String productId;
    private String cartId;


    @BeforeClass
    public void setUp() {
        cartClient    = new CartApiClient(api.getBaseUrl());
        productClient = new ProductApiClient(api.getBaseUrl());
        productId     = productClient.getFirstProductId();
    }

    @BeforeMethod
    public void precondition() {
        cartId = cartClient.createCartAndGetId();
    }


    @Test
    @Story("Delete empty cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that an empty cart can be deleted successfully")
    public void deleteEmptyCartPositiveTest() {
        cartClient.deleteCart(cartId)
                .assertThat().log().all().statusCode(204);
    }


    @Test
    @Story("Delete cart with items")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that cart with items can be deleted and is no longer accessible")
    public void deleteCartWithItemPositiveTest() {
        cartClient.addItemToCart(cartId,
                        CartDataFactory.validCartItem(productId))
                .assertThat().statusCode(200);

        cartClient.deleteCart(cartId)
                .assertThat().statusCode(204);

        cartClient.getCart(cartId)
                .assertThat().statusCode(404);
    }

    @Test
    @Story("Delete cart with invalid ID")
    @Severity(SeverityLevel.NORMAL)
    public void deleteCartWithInvalidIdNegativeTest() {
        cartClient.deleteCart("invalid-cart-id")
                .assertThat().statusCode(404);
    }

    @Test
    @Story("Delete already deleted cart")
    @Severity(SeverityLevel.NORMAL)
    public void deleteAlreadyDeletedCartNegativeTest() {
        cartClient.deleteCart(cartId)
                .assertThat().statusCode(204);

        cartClient.deleteCart(cartId)
                .assertThat().statusCode(404);
    }
}
