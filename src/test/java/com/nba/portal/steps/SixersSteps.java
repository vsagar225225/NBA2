package com.nba.portal.steps;

import org.testng.Assert;

import com.nba.portal.config.ConfigReader;
import com.nba.portal.driver.DriverManager;
import com.nba.portal.pages.SixersPage;
import com.nba.portal.utils.TestDataReader;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class SixersSteps {
    private final ConfigReader config = new ConfigReader();
    private final TestDataReader testDataReader = new TestDataReader();
    private SixersPage sixersPage;
    private int totalSlides;

    @Given("I navigate to the Sixers home page")
    public void iNavigateToTheSixersHomePage() {
        sixersPage = new SixersPage(DriverManager.getDriver(), config.getInt("timeoutSeconds", 20));
        sixersPage.openHomePage(config.get("baseUrl", "https://www.nba.com"));
    }

    @Then("the Sixers page should load successfully")
    public void theSixersPageShouldLoadSuccessfully() {
        Assert.assertTrue(sixersPage.isLoaded(), "Sixers page did not load successfully.");
        Assert.assertTrue(sixersPage.currentUrlContains("sixers"), "Expected URL to contain sixers.");
        Assert.assertTrue(sixersPage.pageContainsText("76ers"), "Expected page to show 76ers.");
    }

    @Then("I count the slides under the tickets section")
    public void iCountTheSlidesUnderTheTicketsSection() {
        totalSlides = sixersPage.getSlideCount();
        System.out.println("Total Sixers ticket slides: " + totalSlides);
        Assert.assertTrue(totalSlides > 0, "Expected at least one Sixers ticket slide.");
    }

    @Then("I validate the slide titles against expected data")
    public void iValidateTheSlideTitlesAgainstExpectedData() {
        sixersPage.validateSlideTitles(testDataReader.getSixersExpectedSlideTitles());
    }

    @Then("I validate the slide duration")
    public void iValidateTheSlideDuration() {
        sixersPage.validateSlideDuration(testDataReader.getSixersExpectedDuration());
    }
}
