package com.toolshop.pages;

import com.toolshop.models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage{
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="email")
    WebElement userField;

    @FindBy (id="password")
    WebElement passwordField;

    public LoginPage enterUserData(User user) {
       type(userField, user.getEmail());
       type(passwordField,user.getPassword());
        return this;
    }



    @FindBy (xpath = "//input[@data-test='login-submit']")
    WebElement loginButton;

    public LoginPage clickOnLoginButton() {
        click(loginButton);
        return this;
    }

    @FindBy(xpath = "//div[@data-test='login-error']")
    WebElement loginErrorMessage;

    public boolean isLoginErrorDisplayed() {
        return isElementDisplayed(loginErrorMessage);
    }
    public String getLoginErrorMessage() {
        return loginErrorMessage.getText().trim();
    }

    @FindBy(xpath = "//div[@data-test='email-error']")
    WebElement invalidEmailMessage;


    public boolean isInvalidEmailMessageDisplayed() {
        return isElementDisplayed(invalidEmailMessage);
    }

    public String getInvalidEmailMessageDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(invalidEmailMessage));
        return invalidEmailMessage.getText().trim();
    }

    @FindBy(xpath = "//div[@data-test='password-error']")
    WebElement invalidPasswordMessage;


    public boolean isInvalidPasswordMessageDisplayed() {
        return isElementDisplayed(invalidPasswordMessage);
    }

    public String getInvalidPasswordMessageDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(invalidPasswordMessage));
        return invalidPasswordMessage.getText().trim();
    }

    @FindBy(xpath = "//*[@data-test='register-link']")
    WebElement registrationLink;

    public RegistrationPage clickOnRegistration(){
        click(registrationLink);
        return new RegistrationPage(driver);
    }

    @FindBy(xpath = "//h3[contains(text(),'Login')]")
    WebElement loginTitle;

    public boolean isLoginTitleDisplayed() {
        return isElementDisplayed(loginTitle);
    }
}
