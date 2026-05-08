package com.toolshop.tests.UI.Cart;

import com.toolshop.pages.HomePage;
import com.toolshop.pages.ProductPage;
import com.toolshop.pages.ShoppingCartPage;
import com.toolshop.tests.UI.TestBase;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Cart")
@Feature("Update Cart")
public class UpdateProductInShoppingCartTest extends TestBase {



    @BeforeMethod
    public void precondition() {
        new HomePage(driver).header.goToHomePage();
        new HomePage(driver).getProductCombinationPliers();
        new ProductPage(driver).clickOnAddToCartButton();
        new ProductPage(driver).isToastMessageDisplayed();
        new ProductPage(driver).isToastMessageDisappeared();
        new ProductPage(driver).header.goToShoppingCartPage();
    }




    @Test
    @Story("Updating quantity recalculates total price")
    @Severity(SeverityLevel.CRITICAL)
    public void updateQuantityTotalRecalculatesTest() {
        ShoppingCartPage cart = new ShoppingCartPage(driver);
        double price = cart.getProductPrice();

        cart.typeQtyProduct("2");

        Assert.assertEquals(
                cart.getCartTotal(),
                price * 2,
                "Cart total did not recalculate correctly after quantity update"
        );
    }

    @Test
    @Story("Decreasing quantity recalculates total price")
    @Severity(SeverityLevel.CRITICAL)
    public void decreaseQuantityTotalRecalculatesTest() {
        ShoppingCartPage cart = new ShoppingCartPage(driver);
        double price = cart.getProductPrice();

        cart.typeQtyProduct("2");
        cart.typeQtyProduct("1");

        Assert.assertEquals(
                cart.getCartTotal(),
                price * 1,
                "Cart total did not recalculate after decreasing quantity"
        );
    }



    @Test
    @Story("Quantity field reflects updated value")
    @Severity(SeverityLevel.NORMAL)
    public void quantityFieldAcceptsNewValueTest() {
        ShoppingCartPage cart = new ShoppingCartPage(driver);

        cart.typeQtyProduct("3");

        Assert.assertEquals(
                cart.getProductQtyValue(),
                "3",
                "Quantity field does not show correct value after update"
        );
    }

    @AfterMethod
    public void postcondition() {
        new ShoppingCartPage(driver).clickOnDeleteButtonIfExists();
        new ShoppingCartPage(driver).header.goToHomePage();
    }

}
