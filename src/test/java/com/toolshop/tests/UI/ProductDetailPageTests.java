package com.toolshop.tests.UI;

import com.toolshop.pages.HomePage;
import com.toolshop.pages.ProductPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Catalog")
@Feature("Product Details")
public class ProductDetailPageTests extends TestBase{

    @BeforeMethod
    public void precondition(){
        new HomePage(driver).getProductCombinationPliers();
    }

    @Test
    @Story("Product name is displayed correctly")
    @Severity(SeverityLevel.CRITICAL)
    public void productNameIsDisplayedTest(){
        Assert.assertEquals(
                new ProductPage(driver).getProductName(),
                "Combination Pliers"
        );
    }

    @Test
    @Story("Product price is displayed correctly")
    @Severity(SeverityLevel.CRITICAL)
    public void productPriceIsDisplayedTest() {
        Assert.assertEquals(
                new ProductPage(driver).getUnitPrice(),
                "14.15"
        );
    }

    @Test
    @Story("Add to cart button is visible")
    @Severity(SeverityLevel.CRITICAL)
    public void addToCartButtonIsDisplayedTest() {
        Assert.assertTrue(
                new ProductPage(driver).isAddToCartButtonDisplayed(),
                "Add to cart button is not displayed"
        );
    }

    @Test
    @Story("Add to favourites button is visible")
    @Severity(SeverityLevel.NORMAL)
    public void addToFavouritesButtonIsDisplayedTest() {
        Assert.assertTrue(
                new ProductPage(driver).isAddToFavouritesButtonDisplayed(),
                "Add to favourites button is not displayed"
        );
    }

    @Test
    @Story("Compare button is visible")
    @Severity(SeverityLevel.NORMAL)
    public void compareButtonIsDisplayedTest() {
        Assert.assertTrue(
                new ProductPage(driver).isCompareButtonDisplayed(),
                "Compare button is not displayed"
        );
    }

    @AfterMethod
    public void postcondition() {
        new ProductPage(driver).header.goToHomePage();
    }
}

