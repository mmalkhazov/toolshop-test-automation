package com.toolshop.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage extends BasePage {
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "btn-add-to-cart")
    WebElement addToCartButton;

    public ProductPage clickOnAddToCartButton() {
        click(addToCartButton);
        return this;
    }

    @FindBy(id = "lblCartCount")
    WebElement shoppingCart;

    public ShoppingCartPage clickOnShopCard() {
        click(shoppingCart);
        return new ShoppingCartPage(driver);

    }

    @FindBy(xpath = "//a[normalize-space()='Home']")
    WebElement homePageButton;

    public HomePage clickOnHomePageButton() {
        click(homePageButton);
        return new HomePage(driver);
    }

    @FindBy(xpath = "//*[@data-test='increase-quantity']")
    WebElement plusButton;

    public ProductPage increaseQtyProduct() {
        click(plusButton);
        return this;
    }


    @FindBy(xpath = "//*[@data-test='decrease-quantity']")
    WebElement minusButton;

    public ProductPage decreaseQtyProduct() {
        click(minusButton);
        return this;
    }

    @FindBy(xpath = "//*[@data-test='product-name']")
    WebElement productName;

    public String getProductName() {
        return productName.getText().trim();
    }

    @FindBy(xpath = "//*[@data-test='unit-price']")
    WebElement unitPrice;

    public String getUnitPrice() {
        return unitPrice.getText().trim();
    }

    @FindBy(xpath = "//*[@data-test='add-to-favorites']")
    WebElement addToFavouritesButton;

    public boolean isAddToFavouritesButtonDisplayed() {

        return addToFavouritesButton.isDisplayed();
    }

    @FindBy(xpath = "//*[@data-test='add-to-compare']")
    WebElement compareButton;

    public boolean isCompareButtonDisplayed() {

        return compareButton.isDisplayed();
    }


    public boolean isAddToCartButtonDisplayed() {

        return addToCartButton.isDisplayed();
    }


    @FindBy(xpath = "//*[@aria-label='Product added to shopping cart.']")
    WebElement toastMessage;

    public boolean isToastMessageDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOf(toastMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isToastMessageDisappeared() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.invisibilityOf(toastMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
