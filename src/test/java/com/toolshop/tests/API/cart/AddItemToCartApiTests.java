package com.toolshop.tests.API.cart;

import com.toolshop.client.CartApiClient;
import com.toolshop.client.ProductApiClient;
import com.toolshop.dto.carts.CartItemResponseDto;
import com.toolshop.tests.API.ApiTestBase;
import com.toolshop.utils.CartDataFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("API")
@Feature("Cart API")
public class AddItemToCartApiTests extends ApiTestBase {
    private String cartId;
    private String productId;



    @BeforeClass
    public void setUp() {
        cartClient    = new CartApiClient(api.getBaseUrl());
        productClient = new ProductApiClient(api.getBaseUrl());
        productId     = productClient.getFirstProductId();
    }

    @BeforeMethod
    public void precondition() {
        // fresh cart for every test
        cartId = cartClient.createCartAndGetId();
    }

    @Test
    @Story("Add item to cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a valid product can be added to cart successfully")
    public void addItemToCartPositiveTest() {
        CartItemResponseDto response = cartClient.addItemToCart(cartId, CartDataFactory.validCartItem(productId))
                .assertThat().log().all().statusCode(200)
                .extract().as(CartItemResponseDto.class);

        Assert.assertEquals(response.getResult(), "item added or updated");
    }




    @Test
    @Story("Add item without product ID")
    @Severity(SeverityLevel.NORMAL)
    @Description("API currently returns 302 redirect instead of validation error (expected 422)")
    public void addItemWithoutProductIdNegativeTest() {
        // BUG: API returns 302 redirect instead of expected 422 validation error
        // Expected: 422 with {"product_id": ["The product id field is required."]}
        // Actual:   302 redirect to https://api.practicesoftwaretesting.com
        // Severity: Medium — API lacks proper input validation for cart item requests
        cartClient.addItemToCart(cartId,
                        CartDataFactory.cartItemWithoutProductId())
                .assertThat().statusCode(302); // ← real behavior documented
    }

    @Test
    @Story("Add item with zero quantity")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify API behavior when quantity is zero (currently returns 302 instead of validation error)")
    public void addItemWithZeroQuantityNegativeTest() {
        cartClient.addItemToCart(cartId,
                        CartDataFactory.cartItemWithZeroQuantity(productId))
                .assertThat().log().all().statusCode(302);
    }
}
