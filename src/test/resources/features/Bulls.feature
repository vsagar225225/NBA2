Feature: Chicago Bulls Portal Page

  @bulls @team @smoke
  Scenario: Verify Bulls portal page loads
<<<<<<< HEAD
    Given I open the NBA portal page "/bulls"
    Then the NBA portal should load successfully
    And the page url should contain "bulls"
    And the page should show "Bulls"
=======
    Given I navigate to the Bulls home page
    Then the Bulls page should load successfully
>>>>>>> ea37d4f (Updated project with latest changes)

  @bulls @test3
  Scenario: Validate Footer Links
    Given I navigate to the Bulls home page
    When I scroll to the footer section
    Then I extract all links from categories
    And I save the links to a CSV file
    And I detect and report duplicate links
