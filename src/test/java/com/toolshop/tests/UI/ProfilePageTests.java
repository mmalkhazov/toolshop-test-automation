package com.toolshop.tests.UI;

import com.toolshop.data.UserLoginData;
import com.toolshop.pages.HomePage;
import com.toolshop.pages.ProfilePage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("User Profile")
@Feature("Profile Page")
public class ProfilePageTests extends TestBase {

    @BeforeMethod
    public void precondition() {
        new HomePage(driver).header.goToLoginInPage()
                .enterUserData(new UserLoginData().defaultUser())
                .clickOnLoginButton();
    }


    @Test
    @Story("User is successfully logged in")
    @Severity(SeverityLevel.CRITICAL)
    public void userIsLoggedInPositiveTest() {
        Assert.assertTrue(
                new ProfilePage(driver).isUserLoggedIn(),
                "User is not logged in"
        );
    }

    @Test
    @Story("User name is displayed on profile page")
    @Severity(SeverityLevel.NORMAL)
    public void userNameIsDisplayedPositiveTest() {
        new ProfilePage(driver).verifyUserName("John Doe");
    }

    @Test
    @Story("Dashboard title is displayed correctly")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the correct dashboard title is shown after successful login")
    public void adminDashboardTitleIsDisplayedPositiveTest() {
        Assert.assertEquals(
                new ProfilePage(driver).getPageTitle(),
                "Sales over the years",
                "Dashboard title is incorrect"
        );
    }

}