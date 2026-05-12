package com.toolshop.pages.components;

import com.toolshop.pages.ContactPage;
import com.toolshop.pages.HomePage;
import com.toolshop.pages.LoginPage;
import com.toolshop.pages.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Header  {
    WebDriver driver;
    public Header(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@data-test='nav-home']")
    WebElement homePageButton;

    public HomePage goToHomePage() {
        homePageButton.click();
        return new HomePage(driver);
    }

    @FindBy(xpath="//a[@data-test='nav-contact']")
    WebElement contactButton;

    public ContactPage goToContactPage(){
        contactButton.click();
        return new ContactPage(driver);
    }

    @FindBy(xpath="//a[@data-test='nav-sign-in']")
    WebElement LoginButton;

    public LoginPage goToLoginInPage(){
        LoginButton.click();
        return new LoginPage(driver);
    }

    @FindBy(xpath="//a[@data-test='nav-cart']")
    WebElement shoppingCart;

    public ShoppingCartPage goToShoppingCartPage(){
        shoppingCart.click();
        return new ShoppingCartPage(driver);
    }


    @FindBy (id="menu")
    WebElement userName;

    @FindBy(xpath = "//a[@data-test='nav-sign-out']")
    WebElement signOutButton;


    public HomePage clickOnSignOutButton(){
        userName.click();
        signOutButton.click();
        return new HomePage(driver);
    }

    @FindBy(id = "lblCartCount")
    WebElement cartBadge;

    public String getCartBadgeCount() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(cartBadge));
        return cartBadge.getText().trim();
    }

    public boolean isCartBadgeDisplayed() {
        try {
            return cartBadge.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
