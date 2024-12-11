package com.ilcarro.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LetCarWorkPage extends BasePage {

    public LetCarWorkPage(WebDriver driver) {
        super(driver);
    }

    // Локаторы
    @FindBy(id = "pickUpPlace")
    private WebElement pickUpPlaceField;

    @FindBy(id = "make")
    private WebElement makeField;

    @FindBy(id = "model")
    private WebElement modelField;

    @FindBy(id = "year")
    private WebElement yearField;

    @FindBy(id = "seats")
    private WebElement seatsField;

    @FindBy(id = "class")
    private WebElement classField;

    @FindBy(id = "serialNumber")
    private WebElement serialNumberField;

    @FindBy(id = "price")
    private WebElement priceField;

    @FindBy(id = "photos")
    private WebElement photosUploadField;

    @FindBy(css = "#fuel > option:nth-child(2)")
    private WebElement fuelOption;

    @FindBy(css = "body > app-root > app-navigator > app-let-car-work > div > form > button")
    private WebElement submitButton;

    @FindBy(css = ".success-message") // Укажите правильный селектор для сообщения об успехе
    private WebElement successMessage;

    /**
     * Заполнение всех обязательных полей формы и отправка.
     */
    public LetCarWorkPage fillAndSubmitForm(String pickUpPlace, String make, String model, String year,
                                            String seats, String carClass, String serialNumber, String price, String photoPath) {
        // Заполнение текстовых полей
        type(pickUpPlaceField, pickUpPlace);
        type(makeField, make);
        type(modelField, model);
        type(yearField, year);
        type(seatsField, seats);
        type(classField, carClass);
        type(serialNumberField, serialNumber);
        type(priceField, price);

        // Загрузка фото
        if (photoPath != null && !photoPath.isEmpty()) {
            photosUploadField.sendKeys(photoPath);
        }

        // Выбор топлива
        if (fuelOption.isDisplayed() && fuelOption.isEnabled()) {
            fuelOption.click();
        } else {
            throw new IllegalStateException("Fuel option is not available or enabled.");
        }

        // Ожидание активации кнопки и её нажатие
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();

        return this;
    }

    /**
     * Проверка успешного добавления автомобиля.
     * @return true, если сообщение об успехе отображается
     */
    public boolean isCarAddedSuccessfully() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOf(successMessage));
            return successMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

