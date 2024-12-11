package com.ilcarro.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Локаторы
    @FindBy(css = "h1")
    private WebElement homePageTitle;

    @FindBy(xpath = "//a[.=' Log in ']")
    private WebElement loginLink;

    @FindBy(xpath = "//a[.='Let the car work']") // Уточните локатор в DevTools, если нужно
    private WebElement letCarWorkLink;

    @FindBy(css = "header .user-icon") // Уточните локатор для элемента, отображающего, что пользователь залогинен
    private WebElement userIcon;

    /**
     * Проверка отображения заголовка главной страницы.
     *
     * @return HomePage
     */
    public HomePage isHomePageTitleDisplayed() {
        assert isElementDisplayed(homePageTitle) : "Home page title is not displayed";
        return this;
    }

    /**
     * Получение текста заголовка главной страницы.
     *
     * @return текст заголовка
     */
    public String getHomePageTitleText() {
        return homePageTitle.getText();
    }

    /**
     * Переход на страницу логина.
     *
     * @return LoginPage
     */
    public LoginPage clickOnLoginLink() {
        assert isElementDisplayed(loginLink) : "Login link is not displayed";
        click(loginLink);
        return new LoginPage(driver);
    }

    /**
     * Переход на страницу "Let the car work".
     *
     * @return LetCarWorkPage
     */
    public LetCarWorkPage navigateToLetCarWorkPage() {
        assert isElementDisplayed(letCarWorkLink) : "Let the car work link is not displayed";
        click(letCarWorkLink);
        return new LetCarWorkPage(driver);
    }

    /**
     * Проверяет, выполнен ли вход пользователя.
     *
     * @return true, если пользователь залогинен
     */
    public boolean isUserLoggedIn() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(userIcon));
            return userIcon.isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("Иконка пользователя не найдена. Проверяем модальное окно.");
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-class"))); // Уточните селектор
                System.out.println("Модальное окно подтверждает успешный вход.");
                closeSuccessModal(); // Закрываем окно
                return true;
            } catch (Exception ex) {
                System.err.println("Модальное окно не появилось или не найдено.");
                return false;
            }
        }
    }

    public void closeSuccessModal() {
        try {
            // Попробуем найти и закрыть окно через кнопку (если она есть)
            WebElement closeButton = driver.findElement(By.cssSelector(".modal-close-button")); // Замените на точный локатор
            closeButton.click();
        } catch (Exception e) {
            System.out.println("Кнопка закрытия модального окна не найдена. Попытка закрыть через JavaScript.");
            // Закрываем через JavaScript, если кнопки нет
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelector('.modal-class').remove();"); // Уточните селектор модального окна
        }
    }

    /**
     * Проверяет наличие модального окна успешного входа с помощью JavaScript.
     *
     * @return true, если модальное окно отображается с текстом успешного входа
     */
    public boolean isSuccessModalDisplayed() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String modalText = (String) js.executeScript(
                    "return Array.from(document.querySelectorAll('div')).find(e => e.innerText.includes('Успешно выполнен вход'))?.innerText;"
            );
            if (modalText != null && modalText.contains("Успешно выполнен вход")) {
                System.out.println("Модальное окно подтверждает успешный вход: " + modalText);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Ошибка при проверке модального окна: " + e.getMessage());
        }
        return false;
    }
}
