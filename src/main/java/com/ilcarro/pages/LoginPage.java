package com.ilcarro.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "email")
    WebElement emailField;

    @FindBy(id = "password")
    WebElement passwordField;

    public LoginPage enterData(String email, String password) {
        type(emailField, email);
        type(passwordField, password);
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

    @FindBy(css = ".modal-overlay")
    WebElement modalOverlay;

    public LoginPage closeSuccessModalWithOverlayClick() {
        click(modalOverlay);
        return this;
    }

    public LoginPage closeSuccessModalByCoordinates() {
        System.out.println("Attempting to click at coordinates (500, 300)");
        Actions actions = new Actions(driver);
        actions.moveByOffset(500, 300).click().perform();
        return this;
    }
}
