Feature: NBA Portal Home Page

  @home @smoke
  Scenario: Verify NBA portal home page loads
    Given I open the NBA portal home page
    Then the NBA portal should load successfully
    And the page url should contain "nba.com"
    And the page should show "NBA"
