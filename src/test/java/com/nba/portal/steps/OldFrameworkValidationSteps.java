package com.nba.portal.steps;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import org.testng.Assert;

import com.nba.portal.config.ConfigReader;
import com.nba.portal.driver.DriverManager;
import com.nba.portal.pages.BullsPage;
import com.nba.portal.pages.SixersPage;
import com.nba.portal.pages.WarriorsPage;
import com.nba.portal.utils.TestDataReader;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OldFrameworkValidationSteps {
    private final ConfigReader config = new ConfigReader();
    private final TestDataReader testDataReader = new TestDataReader();

    private WarriorsPage warriorsPage;
    private SixersPage sixersPage;
    private BullsPage bullsPage;

    private int totalVideoFeeds;
    private int videosAtLeastThreeDaysOld;
    private int totalSlides;
    private int totalFooterLinks;
    private Path footerLinksCsvPath;
    private Set<String> duplicateFooterLinks;

    @Given("I navigate to the Warriors home page")
    public void iNavigateToTheWarriorsHomePage() {
        warriorsPage = new WarriorsPage(DriverManager.getDriver(), config.getInt("timeoutSeconds", 20));
        warriorsPage.openHomePage(config.get("baseUrl", "https://www.nba.com"));
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
        Assert.assertTrue(videosAtLeastThreeDaysOld >= 0,
                "Videos greater than or equal to 3 days count should not be negative.");
    }

    @Given("I navigate to the Sixers home page")
    public void iNavigateToTheSixersHomePage() {
        sixersPage = new SixersPage(DriverManager.getDriver(), config.getInt("timeoutSeconds", 20));
        sixersPage.openHomePage(config.get("baseUrl", "https://www.nba.com"));
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

    @Given("I navigate to the Bulls home page")
    public void iNavigateToTheBullsHomePage() {
        bullsPage = new BullsPage(DriverManager.getDriver(), config.getInt("timeoutSeconds", 20));
        bullsPage.openHomePage(config.get("baseUrl", "https://www.nba.com"));
    }

    @When("I scroll to the footer section")
    public void iScrollToTheFooterSection() {
        bullsPage.scrollToFooter();
    }

    @Then("I extract all links from categories")
    public void iExtractAllLinksFromCategories() {
        totalFooterLinks = bullsPage.extractFooterLinks();
        System.out.println("Total Bulls footer links: " + totalFooterLinks);
        Assert.assertTrue(totalFooterLinks > 0, "Expected at least one Bulls footer link.");
    }

    @Then("I save the links to a CSV file")
    public void iSaveTheLinksToACsvFile() {
        footerLinksCsvPath = bullsPage.saveLinksToCsv();
        Assert.assertTrue(Files.exists(footerLinksCsvPath), "Footer links CSV was not created.");
    }

    @Then("I detect and report duplicate links")
    public void iDetectAndReportDuplicateLinks() {
        duplicateFooterLinks = bullsPage.getDuplicateLinks();
        System.out.println("Duplicate Bulls footer links found: " + duplicateFooterLinks.size());
    }
}
