Feature: Philadelphia 76ers Portal Page

  @sixers @team @smoke
  Scenario: Verify Sixers portal page loads
    Given I open the NBA portal page "/sixers"
    Then the NBA portal should load successfully
    And the page url should contain "sixers"
    And the page should show "76ers"

  @sixers @test2
  Scenario: Validate Tickets Slides
    Given I navigate to the Sixers home page
    Then I count the slides under the tickets section
    And I validate the slide titles against expected data
    And I validate the slide duration
