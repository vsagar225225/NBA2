Feature: Chicago Bulls Portal Page

  @bulls @team @smoke
  Scenario: Verify Bulls portal page loads
    Given I open the NBA portal page "/bulls"
    Then the NBA portal should load successfully
    And the page url should contain "bulls"
    And the page should show "Bulls"

  @bulls @test3
  Scenario: Validate Footer Links
    Given I navigate to the Bulls home page
    When I scroll to the footer section
    Then I extract all links from categories
    And I save the links to a CSV file
    And I detect and report duplicate links
