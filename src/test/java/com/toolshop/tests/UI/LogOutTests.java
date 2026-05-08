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
@Feature("Logout")
public class LogOutTests extends TestBase{

    @BeforeMethod
    public void precondition() {
        new HomePage(driver).header.goToLoginInPage()
                .enterUserData(new UserLoginData().defaultUser())
                .clickOnLoginButton();
    }


    @Test
    @Story("User logs out successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a logged-in user can successfully log out and is redirected to the login page")
    public void userCanLogOutPositiveTest() {
        new ProfilePage(driver).header.clickOnSignOutButton();
        Assert.assertTrue(
                new LoginPage(driver).isLoginTitleDisplayed(),
                "Login page not displayed after logout"
        );
    }



    @Test
    @Story("User session is cleared after logout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that user session is invalidated after logout and user is no longer recognized as logged in")
    public void userIsNotLoggedInAfterLogoutTest() {
        new ProfilePage(driver).header.clickOnSignOutButton();
        Assert.assertFalse(
                new ProfilePage(driver).isUserLoggedIn(),
                "User is still logged in after logout"
        );
    }
}
