package com.ilcarro.stepDefinitions;

import com.ilcarro.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.ilcarro.pages.BasePage.driver;

public class HomePageSteps {

    @Given("User launches Chrome browser")
    public void launch_Chrome_browser() {
        new HomePage(driver).launchBrowser();
    }

    @When("User opens ilCarro HomePage")
    public void open_ilCarro_HomePage() {
        new HomePage(driver).openURL();
    }

    @Then("User verifies HomePage title")
    public void verify_HomePage_title() {
        new HomePage(driver).isHomePageTitleDisplayed();
    }

    @Then("User verifies user is logged in")
    public void userVerifiesUserIsLoggedIn() {
        HomePage homePage = new HomePage(driver);
        boolean isLoggedIn = homePage.isUserLoggedIn();
        if (isLoggedIn) {
            System.out.println("User is successfully logged in.");
        } else {
            System.err.println("Login verification failed! User is not logged in.");
        }
        assert isLoggedIn : "User is not logged in!";
    }

}
