package com.ilcarro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class BasePage {

    public static WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void launchBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void openURL() {
        driver.get("https://ilcarro.web.app");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void click(WebElement element) {
        element.click();
    }

    public void type(WebElement element, String text) {
        if (text != null) {
            click(element);
            element.clear();
            element.sendKeys(text);
        }
    }

    public WebElement findElementByCss(String cssSelector) {
        try {
            return driver.findElement(By.cssSelector(cssSelector));
        } catch (NoSuchElementException e) {
            System.err.println("Element not found using CSS Selector: " + cssSelector);
            throw e; // Логируйте и выбрасывайте исключение для отладки
        }
    }

    public boolean isElementDisplayed(WebElement element){
        try {
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException ex) {
            ex.getMessage();
            return false;
        }
    }
}
