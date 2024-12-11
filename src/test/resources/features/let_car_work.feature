@addCar
Feature: Add a car listing

  Scenario: Add a car after logging in
    Given User launches Chrome browser
    When User opens ilCarro HomePage
    And User logs in with valid credentials
    Then User verifies user is logged in
    And User navigates to "Let the car work" page
    And User fills in all required fields and submits the form
    Then User verifies the car listing is added successfully
