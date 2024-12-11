Feature: Let the car work functionality

  @validData
  Scenario: Fill out "Let the car work" form
    Given User launches Chrome browser
    When User opens ilCarro HomePage
    And User clicks on Login link
    And User enters valid data
    And User clicks on Yalla button
    And User closes the success modal
    And User navigates to "Let the car work" tab
    And User fills out the form with valid data
    Then User verifies the form submission was successful
