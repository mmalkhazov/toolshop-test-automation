package com.toolshop.pages;

import com.toolshop.models.Customer;
import com.toolshop.models.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ShoppingCartPage extends BasePage {
    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = ("//span[contains(normalize-space(),'Combination Pliers')]"))
    WebElement productName;

    public String getProductName() {
        isElementDisplayed(productName);

        return productName.getText().trim();

    }


    @FindBy(xpath="//*[@class='btn btn-danger']")
    WebElement deleteButton;

    @FindBy (xpath="//app-cart//p[contains(text(),'The cart is empty')]")
    WebElement resultText;

    public ShoppingCartPage clickOnDeleteButton(){
        click(deleteButton);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(visibilityOf(resultText));
        return this;
    }

    public boolean isDeleteButtonDisplayed() {
        try {
            return deleteButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }



    public ShoppingCartPage verifyResultAfterDeleting() {
        Assert.assertTrue(shouldHaveText(resultText, "The cart is empty. Nothing to display.", 5));
        return this;
    }

    @FindBy(xpath="//*[@data-test='product-quantity']")
    WebElement productQty;

    public ShoppingCartPage typeQtyProduct(String qty) {
        String totalBefore = cartTotal.getText();
        type(productQty, qty);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.not(
                        ExpectedConditions.textToBePresentInElement(cartTotal, totalBefore)
                ));
        return this;
    }

    @FindBy(xpath = "//*[@data-test='product-price']")
    WebElement productPrice;

    public double getProductPrice() {
        return Double.parseDouble(
                productPrice.getText().replace("$", "").trim()
        );
    }
    @FindBy(xpath = "//*[@data-test='cart-total']")
    WebElement cartTotal;

    public double getCartTotal() {
        return Double.parseDouble(
                cartTotal.getText().replace("$", "").trim()
        );
    }


    public String getEmptyCartMessage() {
        return resultText.getText().trim();
    }

    public ShoppingCartPage clickOnDeleteButtonIfExists() {
        try {
            click(deleteButton);
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(visibilityOf(resultText));
        } catch (Exception e) {
        }
        return this;
    }

    public String getProductQtyValue() {
        return productQty.getAttribute("value").trim();
    }

    @FindBy(xpath =  "//*[@data-test='proceed-1']")
    WebElement proceedToCheckoutButton;

    public ShoppingCartPage clickOnProceedToCheckoutButton() {
        click(proceedToCheckoutButton);
        return this;
    }

    @FindBy(xpath =  "//*[@data-test='proceed-2']")
    WebElement proceedToBillingAddressFormButton;

    public ShoppingCartPage clickOnProceedToBillingAddressFormButton() {
        click(proceedToBillingAddressFormButton);
        return this;
    }

    @FindBy(xpath =  "//*[@data-test='proceed-3']")
    WebElement proceedToPaymentFormButton;

    public ShoppingCartPage clickOnProceedPaymentFormButton() {
        click(proceedToPaymentFormButton);
        return this;
    }

    @FindBy(id="email")
    WebElement userField;

    @FindBy (id="password")
    WebElement passwordField;

    public ShoppingCartPage enterUserData(User user) {
        type(userField, user.getEmail());
        type(passwordField,user.getPassword());
        return this;
    }

    @FindBy (xpath = "//input[@data-test='login-submit']")
    WebElement loginButton;

    public ShoppingCartPage clickOnLoginButton() {
        click(loginButton);
        return this;
    }

    @FindBy (xpath = "//*[@data-test='finish']")
    WebElement confirmButton;

    public ShoppingCartPage clickOnConfirmButton() {
        click(confirmButton);
        return this;
    }

    @FindBy(id = "country")
    WebElement country;

    @FindBy(id = "postal_code")
    WebElement postalCode;

    @FindBy(id = "house_number")
    WebElement houseNumber;

    @FindBy(id = "street")
    WebElement street;

    @FindBy(id = "city")
    WebElement city;

    @FindBy(id = "state")
    WebElement state;

    public void selectCountry(String countryName) {
        if (countryName != null && !countryName.isEmpty()) {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(country));
            new Select(country).selectByVisibleText(countryName);
        }
    }

    public ShoppingCartPage fillUserBillingAddressForm(Customer customer) {

        selectCountry(customer.getCountry());
        type((postalCode), customer.getPostCode());
        type((houseNumber), customer.getHouseNumber());
        type((street), customer.getStreet());
        type((city), customer.getCity());
        type((state), customer.getState());

        return this;
    }

    @FindBy(id = "payment-method")
    WebElement paymentMethod;

    public ShoppingCartPage selectPaymentMethod(String method) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(paymentMethod));
        new Select(paymentMethod).selectByVisibleText(method);
        return this;
    }


    @FindBy(xpath = "//*[@data-test='payment-success-message']")
    WebElement paymentSuccessMessage;

    public String getPaymentSuccessMessage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(paymentSuccessMessage));
        return paymentSuccessMessage.getText().trim();
    }
}
