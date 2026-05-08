package com.toolshop.pages;

import com.toolshop.models.Customer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage extends BasePage {
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }


    @FindBy(id = "first_name")
    WebElement firstName;

    @FindBy(id = "last_name")
    WebElement lastName;

    @FindBy(id = "dob")
    WebElement dayOfBirth;


    @FindBy(id = "street")
    WebElement street;

    @FindBy(id = "postal_code")
    WebElement postalCode;

    @FindBy(id = "house_number")
    WebElement houseNumber;

    @FindBy(id = "city")
    WebElement city;

    @FindBy(id = "state")
    WebElement state;

    @FindBy(id = "country")
    WebElement country;

    @FindBy(id = "phone")
    WebElement phone;

    @FindBy(id = "email")
    WebElement email;

    @FindBy(id = "password")
    WebElement password;

    @FindBy(xpath = "//*[@data-test='register-submit']")
    WebElement registerButton;

    public void selectCountry(String countryName) {
        if (countryName != null && !countryName.isEmpty()) {
            Select select = new Select(country);
            select.selectByVisibleText(countryName);
        }
    }

    public void fillCustomerRegistrationForm(Customer customer) {

        type((firstName), customer.getFirstName());
        type((lastName), customer.getLastName());
        type((dayOfBirth), customer.getDateOfBirth());
        type((street), customer.getStreet());
        type((postalCode), customer.getPostCode());
        type((houseNumber), customer.getHouseNumber());
        type((city), customer.getCity());
        type((state), customer.getState());
        selectCountry(customer.getCountry());
        type((phone), customer.getPhone());
        type((email), customer.getEmail());
        type((password), customer.getPassword());

    }

    public void clickOnRegisterButton() {
        click(registerButton);
    }


    @FindBy(xpath = "//div[@data-test='first-name-error']")
    WebElement firstNameRequiredError;

    public String getFirstNameErrorMessage() {
        return firstNameRequiredError.getText();
    }

    @FindBy(xpath = "//div[@data-test='last-name-error']")
    WebElement lastNameRequiredError;

    public String getLastNameErrorMessage() {
        return lastNameRequiredError.getText();
    }

    @FindBy(xpath = "//div[@data-test='dob-error']//div[normalize-space()='Date of Birth is required']")
    WebElement dateOfBirthRequiredError;


    public String getDateOfBirthErrorMessage() {
        return dateOfBirthRequiredError.getText();
    }

    @FindBy(xpath = "//div[@data-test='dob-error']//div[normalize-space()='Please enter a valid date in YYYY-MM-DD format.']")
    WebElement dateOfBirthInvalidError;


    public String getDateOfBirthInvalidErrorMessage() {
        return dateOfBirthInvalidError.getText();
    }

    @FindBy(xpath = "//div[@data-test='country-error']")
    WebElement countryRequiredError;


    public String getCountryErrorMessage() {
        return countryRequiredError.getText();
    }

    @FindBy(xpath = "//div[@data-test='postal_code-error']")
    WebElement postalCodeRequiredError;


    public String getPostalErrorMessage() {
        return postalCodeRequiredError.getText();
    }


    @FindBy(xpath = "//div[@data-test='house_number-error']")
    WebElement houseNumberRequiredError;


    public String getHouseNumberErrorMessage() {
        return postalCodeRequiredError.getText();
    }


    @FindBy(id = "street-error")
    WebElement streetRequiredError;


    public String getStreetErrorMessage() {
        waitForVisibility(streetRequiredError);
        return streetRequiredError.getText().trim();
    }



    @FindBy(id = "city-error")
    WebElement cityRequiredError;


    public String getCityErrorMessage() {
        waitForVisibility(cityRequiredError);
        return cityRequiredError.getText().trim();
    }

    @FindBy(id = "state-error")
    WebElement stateRequiredError;


    public String getStateErrorMessage() {
        waitForVisibility(stateRequiredError);
        return stateRequiredError.getText().trim();
    }



    @FindBy(id = "phone-error")
    WebElement phoneRequiredError;


    public String getPhoneErrorMessage() {
        return phoneRequiredError.getText();
    }


    @FindBy(id = "email-error")
    WebElement emailRequiredError;


    public String getEmailErrorMessage() {
        return emailRequiredError.getText();
    }

    @FindBy(xpath = "//div[@data-test='password-error']//div[normalize-space()='Password is required']")
    WebElement passwordRequiredError;


    public String getPasswordErrorMessage() {
        return passwordRequiredError.getText();
    }


    @FindBy(xpath = "//div[@data-test='password-error']//div[normalize-space()='Password can not include invalid characters.']")
    WebElement passwordInvalidError;


    public String getPasswordInvalidErrorMessage() {
//        waitForVisibility(passwordInvalidError);
        return passwordInvalidError.getText().trim();
    }

    @FindBy(xpath = "//div[@data-test='password-error']//div[normalize-space()='Password must be minimal 6 characters long.']")
    WebElement passwordInvalidLengthError;


    public String getPasswordInvalidLengthErrorMessage() {
        return passwordInvalidLengthError.getText();
    }



}
