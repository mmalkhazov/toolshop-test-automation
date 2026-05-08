package com.toolshop.tests.UI.Cart;

import com.toolshop.pages.HomePage;
import com.toolshop.pages.ProductPage;
import com.toolshop.pages.ShoppingCartPage;
import com.toolshop.tests.UI.TestBase;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Cart")
@Feature("Remove from Cart")
public class DeleteProductFromCardTest extends TestBase {


@BeforeMethod
public void precondition() {
    new HomePage(driver).header.goToHomePage();
    new HomePage(driver).getProductCombinationPliers();
    new ProductPage(driver).clickOnAddToCartButton();

    if (new ProductPage(driver).isToastMessageDisplayed()) {
        new ProductPage(driver).isToastMessageDisappeared();
        new ProductPage(driver).header.goToShoppingCartPage();
    }
}



    @Test
    @Story("User removes product from cart")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteItemFromCardTest(){
        new HomePage(driver).header.goToShoppingCartPage();
        new ShoppingCartPage(driver).clickOnDeleteButton().verifyResultAfterDeleting();

    }

    @Test
    @Story("Delete button is visible when cart has items")
    @Severity(SeverityLevel.NORMAL)
    public void deleteButtonIsDisplayedWhenCartHasItemTest() {
        Assert.assertTrue(
                new ShoppingCartPage(driver).isDeleteButtonDisplayed(),
                "Delete button is not displayed when cart has item"
        );
    }

    @Test
    @Story("Cart becomes empty after removing product")
    @Severity(SeverityLevel.CRITICAL)
    public void cartIsEmptyAfterRemovingItemTest() {
        Assert.assertEquals(
                new ShoppingCartPage(driver).clickOnDeleteButton().getEmptyCartMessage(),
                "The cart is empty. Nothing to display."
        );
    }

    @Test
    @Story("Cart badge disappears after removing last product")
    @Severity(SeverityLevel.CRITICAL)
    public void cartBadgeDisappearsAfterRemovingItemTest() {
        new ShoppingCartPage(driver).clickOnDeleteButton();
        Assert.assertFalse(
                new ShoppingCartPage(driver).header.isCartBadgeDisplayed(),
                "Cart badge still visible after removing all items"
        );
    }


}
