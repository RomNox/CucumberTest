package com.ilcarro.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "email")
    WebElement emailField;

    @FindBy(id = "password")
    WebElement passwordField;

    public LoginPage enterData(String email, String password) {
        type(emailField,email);
        type(passwordField,password);
        return this;
    }

    @FindBy(css = "button[type='submit']")
    WebElement yallaButton;

    public LoginPage clickOnYallaButton() {
        click(yallaButton);
        return this;
    }

    @FindBy(css = ".message")
    WebElement successMessage;

    public LoginPage isSuccessTextPresent(String message) {
        assert successMessage.getText().contains(message);
        return this;
    }
}
