package com.toolshop.tests.UI.e2e;

import com.toolshop.data.UserLoginData;
import com.toolshop.data.UserRegistrationData;
import com.toolshop.pages.HomePage;
import com.toolshop.pages.ProductPage;
import com.toolshop.pages.ShoppingCartPage;
import com.toolshop.tests.UI.TestBase;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("E2E UI")
@Feature("Checkout Flow")
public class CheckoutFlowE2ETest extends TestBase {


    @Test
    @Story("User completes full checkout flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Full UI flow: product → cart → login → billing → payment → confirmation")
    public void userCompleteFullCheckoutFlowTest(){

        HomePage homePage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        Allure.step("Open product page");
        homePage.getProductPliers();

        Allure.step("Add product to cart and open cart");
        productPage.clickOnAddToCartButton();
        productPage.clickOnShopCard();

        Allure.step("Proceed to checkout");
        cartPage.clickOnProceedToCheckoutButton();

        Allure.step("Login with existing user");
        cartPage.enterUserData(new UserLoginData().defaultUser());
        cartPage.clickOnLoginButton();

        Allure.step("Fill billing address");
        cartPage.clickOnProceedToBillingAddressFormButton();
        cartPage.fillUserBillingAddressForm(new UserRegistrationData().defaultCustomer());

        Allure.step("Select payment method and confirm");
        cartPage.clickOnProceedPaymentFormButton();
        cartPage.selectPaymentMethod("Cash on Delivery");
        cartPage.clickOnConfirmButton();

        Allure.step("Verify payment success message");
        Assert.assertEquals(
                cartPage.getPaymentSuccessMessage(),
                "Payment was successful"
        );
    }
}
