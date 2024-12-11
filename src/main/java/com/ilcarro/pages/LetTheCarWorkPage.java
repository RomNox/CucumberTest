package com.ilcarro.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class LetTheCarWorkPage extends BasePage {

    public LetTheCarWorkPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(linkText = "Let the car work")
    private WebElement letTheCarWorkTab;

    @FindBy(id = "pickUpPlace")
    private WebElement pickUpPlaceField;

    @FindBy(id = "make")
    private WebElement makeField;

    @FindBy(id = "model")
    private WebElement modelField;

    @FindBy(id = "year")
    private WebElement yearField;

    @FindBy(id = "fuel")
    private WebElement fuelDropdown;

    @FindBy(id = "seats")
    private WebElement seatsField;

    @FindBy(id = "class")
    private WebElement carClassField;

    @FindBy(id = "serialNumber")
    private WebElement serialNumberField;

    @FindBy(id = "price")
    private WebElement priceField;

    @FindBy(id = "photos")
    private WebElement photosInput;

    @FindBy(css = ".success-message")
    private WebElement successMessage;

    public void navigateToTab() {
        click(letTheCarWorkTab);
    }

    public void fillOutForm(String pickUp, String carMake, String carModel, String carYear,
                            String fuelOption, String carSeats, String carClass,
                            String carSerial, String carPrice, String photoPath) {

        type(pickUpPlaceField, pickUp);
        type(makeField, carMake);
        type(modelField, carModel);
        type(yearField, carYear);

        Select selectFuel = new Select(fuelDropdown);
        selectFuel.selectByValue(fuelOption);

        type(seatsField, carSeats);
        type(carClassField, carClass);
        type(serialNumberField, carSerial);
        type(priceField, carPrice);

        photosInput.sendKeys(photoPath);
    }

    public void verifySuccess() {
        assert successMessage.isDisplayed() : "Success message is not displayed!";
    }
}
