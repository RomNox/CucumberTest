package com.ilcarro.stepDefinitions;

import com.ilcarro.pages.LetTheCarWorkPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static com.ilcarro.pages.BasePage.driver;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LetTheCarWorkSteps {

    @When("User navigates to {string} tab")
    public void navigateToTab(String tabName) {
        LetTheCarWorkPage letTheCarWorkPage = new LetTheCarWorkPage(driver);
        if (tabName.equalsIgnoreCase("Let the car work")) {
            letTheCarWorkPage.navigateToTab();
        } else {
            throw new IllegalArgumentException("Unsupported tab name: " + tabName);
        }
    }

    @And("User fills out the form with valid data")
    public void fillOutForm() {
        LetTheCarWorkPage letTheCarWorkPage = new LetTheCarWorkPage(driver);

        String relativePhotoPath = "src/test/resources/imeg/777.png";
        String absolutePhotoPath = System.getProperty("user.dir") + "/" + relativePhotoPath;

        File file = new File(absolutePhotoPath);
        if (!file.exists()) {
            throw new IllegalArgumentException("Файл не найден: " + absolutePhotoPath);
        }

        enterValidAddress("Tel Aviv, Israel");

        letTheCarWorkPage.fillOutForm(
                "Tel Aviv, Israel",  // Адрес
                "Toyota",            // Марка автомобиля
                "Corolla",           // Модель автомобиля
                "2020",              // Год выпуска
                "Diesel",            // Тип топлива
                "4",                 // Количество сидений
                "Sedan",             // Класс автомобиля
                "22345",             // Серийный номер
                "90",                // Цена
                absolutePhotoPath    // Абсолютный путь к изображению
        );

        fillAboutField("This is a description of the car.");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.cssSelector(".files-chip-container mat-chip"),  // Селектор для mat-chip
                    file.getName()
            ));
            System.out.println("Файл успешно загружен: " + file.getName());
        } catch (TimeoutException e) {
            throw new AssertionError("Элемент с загруженным файлом не появился на странице: " + file.getName(), e);
        }
    }

    private void fillAboutField(String aboutText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        By aboutFieldSelector = By.cssSelector("#about");

        try {
            WebElement aboutField = wait.until(ExpectedConditions.visibilityOfElementLocated(aboutFieldSelector));
            aboutField.clear();
            aboutField.sendKeys(aboutText);
            System.out.println("Поле About успешно заполнено.");
        } catch (TimeoutException e) {
            throw new AssertionError("Поле About не появилось на странице.", e);
        }
    }

    @And("User enters valid address in the Location field")
    public void enterValidAddress(String address) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        By locationInputSelector = By.id("pickUpPlace");
        By autocompleteDropdownSelector = By.cssSelector(".pac-item");

        try {
            WebElement locationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(locationInputSelector));
            locationInput.clear();
            locationInput.sendKeys(address);

            wait.until(ExpectedConditions.visibilityOfElementLocated(autocompleteDropdownSelector));
            driver.findElements(autocompleteDropdownSelector).get(0).click();

            wait.until(ExpectedConditions.attributeContains(locationInput, "class", "ng-valid"));
            System.out.println("Адрес успешно введен и выбран из выпадающего списка: " + address);
        } catch (TimeoutException e) {
            throw new AssertionError("Ошибка при вводе адреса: " + address, e);
        }
    }

    @And("User clicks the Submit button")
    public void clickSubmitButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        By submitButtonSelector = By.cssSelector("button[type='submit']");

        try {
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonSelector));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth'});", submitButton);

            wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
            System.out.println("Клик по кнопке Submit выполнен успешно.");
        } catch (TimeoutException e) {
            System.err.println("Кнопка Submit не стала кликабельной в течение ожидания.");
            throw e;
        }
    }

    @Then("User verifies the form submission was successful")
    public void verifyFormSubmission() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            WebElement modalSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'modal-content')]//h1[text()='Car added']")
            ));
            WebElement successDetails = driver.findElement(By.xpath("//div[contains(@class, 'modal-content')]//p"));
            System.out.println("Сообщение об успешном добавлении: " + modalSuccess.getText());
            System.out.println("Детали: " + successDetails.getText());
        } catch (TimeoutException e) {
            try {
                WebElement modalError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class, 'modal-content')]//h1[text()='Car adding failed']")
                ));
                WebElement errorDetails = driver.findElement(By.xpath("//div[contains(@class, 'modal-content')]//p"));
                System.err.println("Ошибка добавления автомобиля: " + modalError.getText());
                System.err.println("Детали ошибки: " + errorDetails.getText());
                throw new AssertionError("Ошибка добавления автомобиля: " + errorDetails.getText());
            } catch (TimeoutException ex) {
                throw new AssertionError("Ни успешное, ни ошибочное модальное окно не появилось.", ex);
            }
        }
    }
}
