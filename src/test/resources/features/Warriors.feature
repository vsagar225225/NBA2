Feature: Golden State Warriors Portal Page

  @warriors @team @smoke
  Scenario: Verify Warriors portal page loads
    Given I navigate to the Warriors home page
    Then the Warriors page should load successfully

  @warriors @test1
  Scenario: Count Videos in New and Features
    Given I navigate to the Warriors home page
    When I navigate to the New and Features section
    Then I count the total video feeds
    And I count the videos with a duration greater than or equal to 3 days
