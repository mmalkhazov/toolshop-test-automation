package com.toolshop.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactPage extends BasePage{
    public ContactPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h3[text()='Contact']")
    WebElement contactTitle;

    public boolean isContactPageTitleDisplayed() {
        return isElementDisplayed(contactTitle);
    }
}
