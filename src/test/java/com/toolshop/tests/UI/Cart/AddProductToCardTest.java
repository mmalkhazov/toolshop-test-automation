package com.toolshop.tests.UI.Cart;

import com.toolshop.pages.ShoppingCartPage;
import com.toolshop.pages.HomePage;
import com.toolshop.pages.ProductPage;
import com.toolshop.tests.UI.TestBase;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Cart")
@Feature("Add to Cart")
public class AddProductToCardTest extends TestBase {


    @BeforeMethod
    public void precondition(){
        new HomePage(driver).header.goToHomePage();
        new HomePage(driver).getProductCombinationPliers();
    }


    @Test
    @Story("User adds single product to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void addToCardTest(){
        new ProductPage(driver).clickOnAddToCartButton().clickOnShopCard();
        new ShoppingCartPage(driver);
        ShoppingCartPage cart = new ShoppingCartPage(driver);
        Assert.assertEquals(cart.getProductName(), ("Combination Pliers"));

    }


    @Test
    @Story("Cart badge updates after adding product")
    @Severity(SeverityLevel.CRITICAL)
    public void cartBadgeUpdatesAfterAddingProductTest() {
        new ProductPage(driver).clickOnAddToCartButton();
        Assert.assertEquals(
                new ProductPage(driver).header.getCartBadgeCount(),
                "1",
                "Cart badge count is incorrect after adding product"
        );
    }

    @Test
    @Story("User adds multiple quantity of product to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void addMultipleQuantityToCartTest() {
        new ProductPage(driver).increaseQtyProduct().clickOnAddToCartButton();
        Assert.assertEquals(
                new ProductPage(driver).header.getCartBadgeCount(),
                "2",
                "Cart badge count is incorrect after adding 2 products"
        );
    }

    @Test
    @Story("Toast message is displayed after adding to cart")
    @Severity(SeverityLevel.NORMAL)
    public void toastMessageDisplayedAfterAddingToCartTest() {
        new ProductPage(driver).clickOnAddToCartButton();
        Assert.assertTrue(
                new ProductPage(driver).isToastMessageDisplayed(),
                "Toast message not displayed after adding to cart"
        );
    }

    @AfterMethod
    public void postcondition() {
        new ProductPage(driver).isToastMessageDisappeared();
        new ProductPage(driver).header.goToShoppingCartPage();
        new ShoppingCartPage(driver).clickOnDeleteButtonIfExists();
        new ShoppingCartPage(driver).header.goToHomePage();
    }
}
