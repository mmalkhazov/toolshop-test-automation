package com.toolshop.pages;

import com.toolshop.pages.components.Header;
import com.toolshop.pages.components.FilterBar;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    WebDriver driver;
    public Header header;
    public FilterBar sortBar;

    public BasePage(WebDriver driver) {

        this.driver = driver;
        this.header = new Header(driver);
        this.sortBar = new FilterBar(driver);

        PageFactory.initElements(driver, this);

    }

    public void click(WebElement element) {
        element.click();
    }

    public void type(WebElement element, String text) {
        if (text != null) {
            click(element);
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            element.sendKeys(Keys.DELETE);
            if (!text.isEmpty()) {
                element.sendKeys(text);
            }
            element.sendKeys(Keys.TAB);
        }
    }

    public boolean shouldHaveText(WebElement element, String text, int time) {
        return new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.textToBePresentInElement(element, text));
    }


    public void pause(int millis) {
        try {
            Thread.sleep((long)millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    public boolean isElementDisplayed(WebElement element){
        try{
            element.isDisplayed();
            return true;
        }catch(NoSuchElementException ex){
            ex.getMessage();
            return false;
        }
    }

    public void waitForVisibility(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }
    public void clearWithJs(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].value = '';" +
                        "arguments[0].dispatchEvent(new Event('input'));" +
                        "arguments[0].dispatchEvent(new Event('change'));",
                element
        );

}
    }