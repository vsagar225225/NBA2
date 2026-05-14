Feature: Philadelphia 76ers Portal Page

  @sixers @team @smoke
  Scenario: Verify Sixers portal page loads
    Given I navigate to the Sixers home page
    Then the Sixers page should load successfully

  @sixers @test2
  Scenario: Validate Tickets Slides
    Given I navigate to the Sixers home page
    Then I count the slides under the tickets section
    And I validate the slide titles against expected data
    And I validate the slide duration
