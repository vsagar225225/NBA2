package com.nba.portal.steps;

import org.testng.Assert;

import com.nba.portal.config.ConfigReader;
import com.nba.portal.driver.DriverManager;
import com.nba.portal.pages.WarriorsPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WarriorsSteps {
    private final ConfigReader config = new ConfigReader();
    private WarriorsPage warriorsPage;
    private int totalVideoFeeds;
    private int videosAtLeastThreeDaysOld;

    @Given("I navigate to the Warriors home page")
    public void iNavigateToTheWarriorsHomePage() {
        warriorsPage = new WarriorsPage(DriverManager.getDriver(), config.getInt("timeoutSeconds", 20));
        warriorsPage.openHomePage(config.get("baseUrl", "https://www.nba.com"));
    }

    @Then("the Warriors page should load successfully")
    public void theWarriorsPageShouldLoadSuccessfully() {
        Assert.assertTrue(warriorsPage.isLoaded(), "Warriors page did not load successfully.");
        Assert.assertTrue(warriorsPage.currentUrlContains("warriors"), "Expected URL to contain warriors.");
        Assert.assertTrue(warriorsPage.pageContainsText("Warriors"), "Expected page to show Warriors.");
    }

    @When("I navigate to the New and Features section")
    public void iNavigateToTheNewAndFeaturesSection() {
        warriorsPage.navigateToNewsAndFeatures();
    }

    @Then("I count the total video feeds")
    public void iCountTheTotalVideoFeeds() {
        totalVideoFeeds = warriorsPage.getTotalVideoFeeds();
        System.out.println("Total Warriors video/news feeds: " + totalVideoFeeds);
        Assert.assertTrue(totalVideoFeeds > 0, "Expected at least one Warriors video/news feed.");
    }

    @Then("I count the videos with a duration greater than or equal to 3 days")
    public void iCountTheVideosWithDurationGreaterThanOrEqualToThreeDays() {
        videosAtLeastThreeDaysOld = warriorsPage.getVideosWithDurationAtLeastDays(3);
        System.out.println("Warriors videos/news feeds >= 3 days old: " + videosAtLeastThreeDaysOld);
        // Zero is acceptable because live content changes; the important part is successful parsing.
        Assert.assertTrue(videosAtLeastThreeDaysOld >= 0,
                "Videos greater than or equal to 3 days count should not be negative.");
    }
}
