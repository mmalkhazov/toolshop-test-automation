package com.toolshop.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[@data-test='nav-sign-in']")
    WebElement login;

    public LoginPage getLogin() {
        click(login);
        return new LoginPage(driver);
    }

    @FindBy(xpath = "//h5[normalize-space()='Combination Pliers']")
    WebElement firstProduct;

    public ProductPage getProductCombinationPliers() {
        click(firstProduct);
        return new ProductPage(driver);

    }

    @FindBy(xpath = "//h5[normalize-space()='Pliers']")
    WebElement secondProduct;

    public ProductPage getProductPliers() {
        click(secondProduct);
        return new ProductPage(driver);

    }
}
