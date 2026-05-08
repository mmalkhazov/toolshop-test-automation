package com.toolshop.tests.UI;

import com.toolshop.data.UserLoginData;
import com.toolshop.pages.HomePage;
import com.toolshop.pages.LoginPage;
import com.toolshop.pages.ProfilePage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("Login")
public class LoginTests extends TestBase {


    @BeforeMethod
    public void precondition() {

        if( new ProfilePage(driver).isUserLoggedIn()){
            new ProfilePage(driver).header.clickOnSignOutButton();
        }
        new HomePage(driver).header.goToLoginInPage();


    }



    @Test
    @Story("Valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void loginPositiveTest() {
        new LoginPage(driver)
                .enterUserData(
                        new UserLoginData()
                                .defaultUser())
                .clickOnLoginButton();
        new ProfilePage(driver).verifyUserName("John Doe");
    }

    @Test
    @Story("Invalid credentials - wrong email")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithWrongEmailNegativeTest() {
        new LoginPage(driver)
                .enterUserData(
                        new UserLoginData()
                                .defaultUser()
                                .setEmail("admi@practicesoftwaretesting.com"))
                .clickOnLoginButton();
        new LoginPage(driver);
        Assert.assertEquals(
                new LoginPage(driver).getLoginErrorMessage(),
                "Invalid email or password"
        );
    }

    @Test
    @Story("Invalid credentials - wrong password")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithWrongPasswordNegativeTest() {
        new LoginPage(driver)
                .enterUserData(
                        new UserLoginData()
                                .defaultUser()
                                .setPassword("welcome0"))
                .clickOnLoginButton();
        new LoginPage(driver);
        Assert.assertEquals(
                new LoginPage(driver).getLoginErrorMessage(),
                "Invalid email or password"
        );
    }


    @Test
    @Story("Invalid credentials - invalid email format")
    @Severity(SeverityLevel.MINOR)
    public void loginWithInvalidEmailNegativeTest() {
        new LoginPage(driver)
                .enterUserData(
                        new UserLoginData()
                                .defaultUser()
                                .setEmail("admipracticesoftwaretesting"))
                .clickOnLoginButton();
        new LoginPage(driver);
        Assert.assertEquals(
                new LoginPage(driver).getInvalidEmailMessageDisplayed(),
                "Email format is invalid"
        );
    }

    @Test
    @Story("Invalid credentials - invalid password format")
    @Severity(SeverityLevel.MINOR)
    public void loginWithInvalidPasswordNegativeTest() {
        new LoginPage(driver)
                .enterUserData(
                        new UserLoginData()
                                .defaultUser()
                                .setPassword("@@"))
                .clickOnLoginButton();
        new LoginPage(driver);
        Assert.assertEquals(
                new LoginPage(driver).getInvalidPasswordMessageDisplayed(),
                "Password length is invalid"
        );
    }


    @Test
    @Story("Empty fields - empty email")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithEmptyEmailFieldNegativeTest() {
        new LoginPage(driver)
                .enterUserData(
                        new UserLoginData()
                                .defaultUser()
                                .setEmail(""))
                .clickOnLoginButton();
        new LoginPage(driver);
        Assert.assertEquals(
                new LoginPage(driver).getInvalidEmailMessageDisplayed(),
                "Email is required"
        );
    }

    @Test
    @Story("Empty fields - empty password")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithEmptyPasswordFieldNegativeTest() {
        new LoginPage(driver)
                .enterUserData(
                        new UserLoginData()
                                .defaultUser()
                                .setPassword(""))
                .clickOnLoginButton();
        new LoginPage(driver);
        Assert.assertEquals(
                new LoginPage(driver).getInvalidPasswordMessageDisplayed(),
                "Password is required"
        );
    }



}
