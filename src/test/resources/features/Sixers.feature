Feature: Philadelphia 76ers Portal Page

  @sixers @team @smoke
  Scenario: Verify Sixers portal page loads
<<<<<<< HEAD
    Given I open the NBA portal page "/sixers"
    Then the NBA portal should load successfully
    And the page url should contain "sixers"
    And the page should show "76ers"
=======
    Given I navigate to the Sixers home page
    Then the Sixers page should load successfully
>>>>>>> ea37d4f (Updated project with latest changes)

  @sixers @test2
  Scenario: Validate Tickets Slides
    Given I navigate to the Sixers home page
    Then I count the slides under the tickets section
    And I validate the slide titles against expected data
    And I validate the slide duration
