package com.toolshop.tests.UI;

import com.toolshop.pages.ContactPage;
import com.toolshop.pages.HomePage;
import com.toolshop.pages.LoginPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Navigation")
@Feature("Header Navigation")
public class NavigationTests extends TestBase{

    @BeforeMethod
    public void precondition() {
        new HomePage(driver).header.goToHomePage();
    }


    @Test
    @Story("Navigate to Home page via header")
    @Severity(SeverityLevel.CRITICAL)
    public void clickHomeNavigatesToHomePageTest() {
        new HomePage(driver).header.goToHomePage();
        Assert.assertFalse(
                new HomePage(driver).sortBar.getNamesFromPage().isEmpty(),
                "Home page products not displayed"
        );
    }

    @Test
    @Story("Navigate to Contact page via header")
    @Severity(SeverityLevel.NORMAL)
    public void clickContactNavigatesToContactPageTest() {
        new HomePage(driver).header.goToContactPage();
        Assert.assertTrue(
                new ContactPage(driver).isContactPageTitleDisplayed(),
                "Contact page not displayed"
        );
    }



    @Test
    @Story("Navigate to Login page via header")
    @Severity(SeverityLevel.CRITICAL)
    public void clickSignInNavigatesToLoginPageTest() {
        new HomePage(driver).header.goToLoginInPage();
        Assert.assertTrue(
                new LoginPage(driver).isLoginTitleDisplayed(),
                "Login page not displayed"
        );
    }
}
