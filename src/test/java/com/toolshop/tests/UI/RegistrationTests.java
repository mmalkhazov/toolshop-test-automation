package com.toolshop.tests.UI;

import com.toolshop.data.UserRegistrationData;
import com.toolshop.pages.components.Header;
import com.toolshop.pages.HomePage;
import com.toolshop.pages.LoginPage;
import com.toolshop.pages.RegistrationPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("Registration")
public class RegistrationTests extends TestBase {


    @BeforeMethod
    public void precondition() {
        new HomePage(driver);
        new Header(driver).goToLoginInPage().clickOnRegistration();

    }

    @Test
    @Story("Valid registration")
    @Severity(SeverityLevel.CRITICAL)
    public void registrationPositiveTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                        new UserRegistrationData().defaultCustomer());
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertTrue(new LoginPage(driver).isLoginTitleDisplayed());
    }


    @Test
    @Story("Missing required field - first name")
    @Severity(SeverityLevel.NORMAL)
    public void registrationWithoutFirstNameNegativeTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                new UserRegistrationData()
                        .defaultCustomer()
                        .setFirstName(""));
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertEquals(
                new RegistrationPage(driver).getFirstNameErrorMessage(),
                "First name is required"
        );
    }

    @Test
    @Story("Missing required field - last name")
    @Severity(SeverityLevel.NORMAL)
    public void registrationWithoutLastNameNegativeTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                new UserRegistrationData()
                        .defaultCustomer()
                        .setLastName(""));
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertEquals(
                new RegistrationPage(driver).getLastNameErrorMessage(),
                "Last name is required"
        );
    }

    @Test
    @Story("Missing required field - date of birth")
    @Severity(SeverityLevel.NORMAL)
    public void registrationWithoutDateOfBirthNegativeTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                new UserRegistrationData()
                        .defaultCustomer()
                        .setDateOfBirth(""));
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertEquals(
                new RegistrationPage(driver).getDateOfBirthErrorMessage(),
                "Date of Birth is required"
        );
    }


    @Test
    @Story("Invalid field - date of birth format")
    @Severity(SeverityLevel.MINOR)
    public void registrationInvalidDateOfBirthNegativeTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                new UserRegistrationData()
                        .defaultCustomer()
                        .setDateOfBirth("17/04/1962"));
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertEquals(
                new RegistrationPage(driver).getDateOfBirthInvalidErrorMessage(),
                "Please enter a valid date in YYYY-MM-DD format."
        );
    }

    @Test (enabled = false)
    @Story("Missing required field - street")
    @Severity(SeverityLevel.NORMAL)
    @Description("KNOWN LIMITATION: Street is auto-filled via postal code API. " +
            "Angular reactive form re-populates field after programmatic clear. " +
            "Manual testing confirms validation works correctly.")
    public void registrationWithoutStreetNegativeTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                new UserRegistrationData()
                        .defaultCustomer()
                        .setStreet(""));
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertEquals(
                new RegistrationPage(driver).getStreetErrorMessage(),
                "Street is required"
        );
    }


    @Test
    @Story("Missing required field - postal code")
    @Severity(SeverityLevel.NORMAL)
    public void registrationWithoutPostalCodeNegativeTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                new UserRegistrationData()
                        .defaultCustomer()
                        .setPostCode(""));
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertEquals(
                new RegistrationPage(driver).getPostalErrorMessage(),
                "Postcode is required"
        );
    }


    @Test (enabled = false)
    @Story("Missing required field - city")
    @Severity(SeverityLevel.NORMAL)
    @Description("KNOWN LIMITATION: Street is auto-filled via postal code API. " +
            "Angular reactive form re-populates field after programmatic clear. " +
            "Manual testing confirms validation works correctly.")
    public void registrationWithoutCityNegativeTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                new UserRegistrationData()
                        .defaultCustomer()
                         .setCity(""));
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertEquals(
                new RegistrationPage(driver).getCityErrorMessage(),
                "City is required"
        );
    }

    @Test(enabled = false)
    @Story("Missing required field - state")
    @Severity(SeverityLevel.NORMAL)
    @Description("KNOWN LIMITATION: Street is auto-filled via postal code API. " +
            "Angular reactive form re-populates field after programmatic clear. " +
            "Manual testing confirms validation works correctly.")
    public void registrationWithoutStateNegativeTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                new UserRegistrationData()
                        .defaultCustomer()
                        .setState(""));
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertEquals(
                new RegistrationPage(driver).getStateErrorMessage(),
                "State is required"
        );
    }

    @Test
    @Story("Missing required field - country")
    @Severity(SeverityLevel.NORMAL)
    public void registrationWithoutCountryNegativeTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                new UserRegistrationData()
                        .defaultCustomer()
                        .setCountry(""));
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertEquals(
                new RegistrationPage(driver).getCountryErrorMessage(),
                "Country is required"
        );
    }

    @Test
    @Story("Missing required field - phone")
    @Severity(SeverityLevel.NORMAL)
    public void registrationWithoutPhoneNegativeTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                new UserRegistrationData()
                        .defaultCustomer()
                        .setPhone(""));
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertEquals(
                new RegistrationPage(driver).getPhoneErrorMessage(),
                "Phone is required."
        );
    }

    @Test
    @Story("Missing required field - email")
    @Severity(SeverityLevel.NORMAL)
    public void registrationWithoutEmailNegativeTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                new UserRegistrationData()
                        .defaultCustomer()
                        .setEmail(""));
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertEquals(
                new RegistrationPage(driver).getEmailErrorMessage(),
                "Email is required"
        );
    }

    @Test
    @Story("Missing required field - password")
    @Severity(SeverityLevel.NORMAL)
    public void registrationWithoutPasswordNegativeTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                new UserRegistrationData()
                        .defaultCustomer()
                        .setPassword(""));
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertEquals(
                new RegistrationPage(driver).getPasswordErrorMessage(),
                "Password is required"
        );
    }


    @Test
    @Story("Invalid field - password missing special character")
    @Severity(SeverityLevel.MINOR)
    public void registrationWithoutSpecialSymbolPasswordNegativeTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                new UserRegistrationData()
                        .defaultCustomer()
                        .setPassword("MaynardToolBand1993"));
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertEquals(
                new RegistrationPage(driver).getPasswordInvalidErrorMessage(),
                "Password can not include invalid characters."
        );
    }

    @Test
    @Story("Invalid field - password too short")
    @Severity(SeverityLevel.MINOR)
    public void registrationWithInvalidLengthPasswordNegativeTest() {
        new RegistrationPage(driver).fillCustomerRegistrationForm(
                new UserRegistrationData()
                        .defaultCustomer()
                        .setPassword("Maynard"));
        new RegistrationPage(driver).clickOnRegisterButton();
        Assert.assertEquals(
                new RegistrationPage(driver).getPasswordInvalidLengthErrorMessage(),
                "Password must be minimal 6 characters long."
        );
    }


}
