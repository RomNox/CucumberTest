package com.ilcarro.stepDefinitions;

import com.ilcarro.pages.HomePage;
import com.ilcarro.pages.LetCarWorkPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.ilcarro.pages.BasePage.driver;

public class LetCarWorkSteps {

    @And("User logs in with valid credentials")
    public void userLogsInWithValidData() {
        HomePage homePage = new HomePage(driver);
        homePage.clickOnLoginLink(); // Переход на страницу логина
        LoginSteps loginSteps = new LoginSteps();
        loginSteps.enter_valid_data();    // Ввод данных
        loginSteps.click_on_Yalla_button(); // Подтверждение логина
    }

    @When("User navigates to {string} page")
    public void userNavigatesToPage(String pageName) {
        HomePage homePage = new HomePage(driver);
        if (pageName.equalsIgnoreCase("Let the car work")) {
            homePage.navigateToLetCarWorkPage();
        } else {
            throw new IllegalArgumentException("Unsupported page: " + pageName);
        }
    }

    @And("User fills in all required fields and submits the form")
    public void userFillsInAllFieldsAndSubmitsForm() {
        LetCarWorkPage letCarWorkPage = new LetCarWorkPage(driver);
        letCarWorkPage.fillAndSubmitForm(
                "Herzl Street 15, Tel Aviv, Israel",  // pickUpPlace
                "Toyota",            // make
                "Corolla",           // model
                "2020",              // year
                "5",                 // seats
                "Standard",          // car class
                "SN123456",          // serial number
                "150",               // price
                "/path/to/photo.jpg" // photoPath
        );
    }

    @Then("User verifies the car listing is added successfully")
    public void userVerifiesTheCarListingIsAddedSuccessfully() {
        LetCarWorkPage letCarWorkPage = new LetCarWorkPage(driver);
        boolean isSuccessful = letCarWorkPage.isCarAddedSuccessfully();
        if (isSuccessful) {
            System.out.println("Car listing was added successfully!");
        } else {
            throw new AssertionError("Car listing was not added successfully!");
        }
    }
}
