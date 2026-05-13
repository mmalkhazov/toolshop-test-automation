package com.toolshop.config;

import com.google.common.io.Files;
import com.toolshop.models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class ApplicationManager {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationManager.class);


    String browser;
    public WebDriver driver;

    User user;

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public WebDriver starTest() {
        if (browser.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                logger.info("Launching Chrome in headless mode");
                options.addArguments(
                        "--headless=new",
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--disable-gpu",
                        "--window-size=1920,1080"
                );
            }
            driver = new ChromeDriver(options);
        } else if (browser.equals("firefox")) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addPreference("intl.accept_languages", "en-US, en");
            driver = new FirefoxDriver(firefoxOptions);

        } else {
            throw new IllegalArgumentException("Browser entered is not correct");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("https://practicesoftwaretesting.com/");

        // Wait for page to fully load
        new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(30))
                .until(d -> ((org.openqa.selenium.JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));

        return driver;
    }

    public void stopTest() {
        if (driver != null) {
            driver.quit();
        }
    }



    public User getUser() {
        return user;
    }

    public String takeScreenshot() {

        File tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File screenshot = new File("screenshots/screen-" + System.currentTimeMillis() + ".png");

        try {
            Files.copy(tmp,screenshot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return screenshot.getAbsolutePath();
    }

    public void navigateToHome() {
        driver.get("https://practicesoftwaretesting.com/");
    }

}



