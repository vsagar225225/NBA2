Feature: Chicago Bulls Portal Page

  @bulls @team @smoke
  Scenario: Verify Bulls portal page loads
    Given I navigate to the Bulls home page
    Then the Bulls page should load successfully

  @bulls @test3
  Scenario: Validate Footer Links
    Given I navigate to the Bulls home page
    When I scroll to the footer section
    Then I extract all links from categories
    And I save the links to a CSV file
    And I detect and report duplicate links
