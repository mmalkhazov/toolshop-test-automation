package com.toolshop.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.NoSuchElementException;

import java.time.Duration;

public class ProfilePage extends BasePage{
    public ProfilePage(WebDriver driver) {
        super(driver);
    }


    @FindBy(id = "menu")
    WebElement userName;

    public boolean isUserLoggedIn() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOf(userName));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ProfilePage verifyUserName(String text) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(userName));
        Assert.assertTrue(userName.getText().trim().contains(text));
        return this;
    }

    @FindBy(xpath = "//*[@data-test='page-title']")
    WebElement pageTitle;

    public String getPageTitle() {
        return pageTitle.getText().trim();
    }



}
