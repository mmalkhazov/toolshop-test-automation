package com.toolshop.tests.API.e2e;

import com.toolshop.client.CartApiClient;
import com.toolshop.client.ProductApiClient;
import com.toolshop.dto.carts.CartGetResponseDto;
import com.toolshop.dto.user.RegisterRequestDto;
import com.toolshop.tests.API.ApiTestBase;
import com.toolshop.utils.CartDataFactory;
import com.toolshop.utils.TestDataFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.toolshop.client.UserApiClient;

@Epic("E2E API")
@Feature("Shopping Flow API")
public class ShoppingFlowE2ETest extends ApiTestBase {
    private String productId;
    private String cartId;

    @BeforeClass
    public void precondition() {

        userClient = new UserApiClient(api.getBaseUrl(), api.getToken());
        productClient = new ProductApiClient(api.getBaseUrl()); // ← ADD THIS
        cartClient = new CartApiClient(api.getBaseUrl());
    }

    @Test
    @Story("Full shopping flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("End-to-end test: register → login → add product to cart → verify → delete cart")
    public void fullShoppingFlowE2ETest() {
        RegisterRequestDto newUser = TestDataFactory.createValidUserRequest();

        Allure.step("Register new user: " + newUser.getEmail());
        userClient.registerUser(newUser)
                .assertThat()
                .log().all()
                .statusCode(201);

        Allure.step("Login user and generate token");
        String userToken = userClient.loginAndGetToken(
                newUser.getEmail(),
                newUser.getPassword()
        );

        Allure.step("Fetch first available product");
        productId = productClient.getFirstProductId();

        Allure.step("Create new cart");
        cartId = cartClient.createCartAndGetId();


        Allure.step("Add product to cart");
        cartClient.addItemToCart(cartId, CartDataFactory.validCartItem(productId))
                .assertThat().statusCode(200);

        Allure.step("Verify cart contents");
        CartGetResponseDto response = cartClient.getCart(cartId)
                .assertThat().log().all().statusCode(200)
                .extract().as(CartGetResponseDto.class);

        Assert.assertFalse(response.getCart_items().isEmpty());
        Assert.assertEquals(response.getCart_items().get(0).getProduct_id(), productId);
        Assert.assertEquals(response.getCart_items().get(0).getQuantity(), 1);

        Allure.step("Delete cart and verify removal");
        cartClient.deleteCart(cartId)
                .assertThat().statusCode(204);

        cartClient.getCart(cartId)
                .assertThat().statusCode(404);
        System.out.println("✅ Step 7 — cart deleted");

    }
}
