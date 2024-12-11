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
}
